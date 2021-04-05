package com.construction.organization.payment.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.PaymentEntryStatus;
import com.construction.organization.payment.repository.PaymentEntryRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.construction.organization.payment.domain.PaymentEntryStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentEntryService {

    private final EntityDataMapper dataMapper;
    private final PaymentEntryRepository repository;
    private final StatusHistoryService historyService;
    private final ApplicationSecurityContext context;
    private final TaskRepository taskRepository;
    private final EntityManager entityManager;

    public List<PaymentEntry> search(final Long boqId,
                                     final Long projectId,
                                     final Long streetId,
                                     final Long houseId,
                                     final Long subConstructorId,
                                     final PaymentEntryStatus status,
                                     final Pageable pageable) {
        final StringBuilder sql = new StringBuilder("select pe.* from payment_entry pe join task t on pe.task_id = t.id");
        if (status != null) {
            sql.append(" and pe.status like '").append(status).append("'");
        }
        if (subConstructorId != null) {
            sql.append(" and pe.payment_request_id in (select pr.id from payment_request pr where pr.sub_constructor_id = ")
                    .append(subConstructorId)
                    .append(")");
        }
        if (boqId != null || projectId != null || streetId != null || houseId != null) {
            sql.append(" and t.boq_id in (select b.id from boq b where 1=1");
            if (boqId != null) {
                sql.append(" and b.id = ").append(boqId);
            }
            if (projectId != null) {
                sql.append(" and b.project_id = ").append(projectId);
            }
            if (streetId != null) {
                sql.append(" and b.street_id = ").append(streetId);
            }
            if (houseId != null) {
                sql.append(" and b.house_id = ").append(houseId);
            }
            sql.append(")");
        }
        if (pageable != null) {
            sql.append(" limit ").append(pageable.getPageSize());
            sql.append(" offset ").append(pageable.getOffset());
        }

        return entityManager.createQuery(sql.toString(), PaymentEntry.class).getResultList();
    }

    public List<PaymentEntry> getAll() {
        return repository.findAll();
    }

    public Page<PaymentEntry> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public PaymentEntry getById(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PaymentEntry.class, id));
    }

    public List<PaymentEntry> getByAllId(final List<Long> ids) {
        if (ids.size() > 50) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "too many content");
        }
        return repository.findByIdIn(ids);
    }

    public Page<PaymentEntry> getBySubConstructorId(final Long id, final PaymentEntryStatus status, Pageable pageable) {
        return repository.findAllByPaymentRequestSubConstructorIdAndStatus(id, status, pageable);
    }

    public void handleCommand(final PaymentEntry paymentEntry,
                              final CommandType command,
                              final String attachment,
                              final BigDecimal approveAmount,
                              final String comment) {
        switch (command) {
            case SUBMIT:
                if (!PaymentEntryStatus.OPEN.equals(paymentEntry.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in open status");
                }
                if (approveAmount != null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Approve amount cannot be set on create request");
                }
                paymentEntry.setStatus(SUBMITTED);
                break;
            case VERIFY:
                if (!SUBMITTED.equals(paymentEntry.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in submitted status");
                }
                paymentEntry.setStatus(VERIFIED);
                break;
            case CONFIRM:
                if (!VERIFIED.equals(paymentEntry.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in verified status");
                }
                if (approveAmount != null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Approve amount cannot be set on create request");
                }
                paymentEntry.setStatus(PaymentEntryStatus.CONFIRMED);
                break;
            case REVIEW:
                if (!CONFIRMED.equals(paymentEntry.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in confirmed status");
                }
                if (approveAmount != null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Approve amount cannot be set on create request");
                }
                paymentEntry.setStatus(REVIEWED);
                break;
            case APPROVE:
                if (!REVIEWED.equals(paymentEntry.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in reviewed status");
                }
                if (approveAmount == null || BigDecimal.ZERO.equals(approveAmount)) {
                    paymentEntry.setApprovedAmount(paymentEntry.getRequestAmount());
                } else {
                    // validate approve amount
                    if (approveAmount.compareTo(paymentEntry.getTask().getAvailableAmount()) > 0) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Approve amount cannot be greater than available amount");
                    }
                    paymentEntry.setApprovedAmount(approveAmount);
                }
                paymentEntry.setStatus(APPROVED);
                break;
            case CASH_OUT:
                if (!APPROVED.equals(paymentEntry.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in approved status");
                }
                final var task = paymentEntry.getTask();
                final var paidAmount = task.getPaidAmount().add(paymentEntry.getApprovedAmount());
                final var availableAmount = task.getAvailableAmount().subtract(paidAmount);
                task.setAvailableAmount(availableAmount);
                task.setPaidAmount(paidAmount);
                taskRepository.save(task);
                paymentEntry.setStatus(PAID);
                paymentEntry.setPaidOn(LocalDate.now());
                break;
            case REJECT:
                if (OPEN.equals(paymentEntry.getStatus())
                        || APPROVED.equals(paymentEntry.getStatus())
                        || PAID.equals(paymentEntry.getStatus())) {
                    throw new RuntimeException("status cannot be rejected");
                }
                paymentEntry.setStatus(REJECTED);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "command not found");
        }
        historyService.addHistory(paymentEntry, command, attachment, context.authenticatedUser(), comment);
        repository.save(paymentEntry);
    }

    public Page<PaymentEntry> getAllPending(final Pageable pageable) {
        return repository.findAllByStatusNot(APPROVED, pageable);
    }

    public PaymentEntry update(final Long id, final PaymentEntry sourceEntry) {
        final var targetEntry = getById(id);
        final var status = targetEntry.getStatus();
        if (!OPEN.equals(status)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "entry has been submitted, cannot be deleted");
        }
        final var newEntry = dataMapper.mapObject(sourceEntry, targetEntry, PaymentEntry.class);
        if (newEntry.getStatus() == null) {
            newEntry.setStatus(status);
        }
        historyService.addHistory(newEntry, CommandType.UPDATE, null, context.authenticatedUser(), null);
        return repository.save(newEntry);
    }

    public boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    public Page<PaymentEntry> findPendingFor(final CommandType pendingFor, Pageable pageable) {
        switch (pendingFor) {
            case SUBMIT:
                return repository.findAllByStatus(PaymentEntryStatus.OPEN, pageable);
            case VERIFY:
                return repository.findAllByStatus(PaymentEntryStatus.SUBMITTED, pageable);
            case CONFIRM:
                return repository.findAllByStatus(PaymentEntryStatus.VERIFIED, pageable);
            case REVIEW:
                return repository.findAllByStatus(PaymentEntryStatus.CONFIRMED, pageable);
            case APPROVE:
                return repository.findAllByStatus(PaymentEntryStatus.REVIEWED, pageable);
            case CASH_OUT:
                return repository.findAllByStatus(PaymentEntryStatus.APPROVED, pageable);
        }
        return Page.empty();
    }
}
