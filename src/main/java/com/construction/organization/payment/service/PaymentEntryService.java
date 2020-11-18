package com.construction.organization.payment.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.PaymentEntryStatus;
import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.repository.PaymentEntryRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

import static com.construction.organization.payment.domain.PaymentEntryStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentEntryService {

    private final PaymentEntryRepository repository;
    private final EntityDataMapper dataMapper;
    private final StatusHistoryService historyService;
    private final ApplicationSecurityContext context;

    public PaymentEntry save(final PaymentEntry paymentEntry) {
        return repository.save(paymentEntry);
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

    public PaymentEntry handleCommand(final PaymentEntry paymentEntry, final CommandType command, final String comment) {
        switch (command) {
            case SUBMIT:
                if (!PaymentEntryStatus.OPEN.equals(paymentEntry.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in open status");
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
                paymentEntry.setStatus(PaymentEntryStatus.CONFIRMED);
                break;
            case REVIEW:
                if (!CONFIRMED.equals(paymentEntry.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in confirmed status");
                }
                paymentEntry.setStatus(REVIEWED);
                break;
            case APPROVE:
                if (!REVIEWED.equals(paymentEntry.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in reviewed status");
                }
                paymentEntry.setStatus(APPROVED);
                break;
            case CASH_OUT:
                if (!APPROVED.equals(paymentEntry.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in approved status");
                }
                paymentEntry.setStatus(PAID);
                break;
            case REJECT:
                if (OPEN.equals(paymentEntry.getStatus())
                        || APPROVED.equals(paymentEntry.getStatus())
                        || PAID.equals(paymentEntry.getStatus())) {
                    throw new RuntimeException("status cannot be rejected");
                }
                paymentEntry.setStatus(OPEN);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "command not found");
        }
        historyService.addHistory(paymentEntry, command, context.authenticatedUser(), comment);
        return repository.save(paymentEntry);
    }

    public Page<PaymentEntry> getAllPending(final Pageable pageable) {
        return repository.findAllByStatusNot(PaymentEntryStatus.APPROVED, pageable);
    }

    public PaymentEntry update(final Long id, final PaymentEntry sourceEntry) {
        final var targetEntry = getById(id);
        final var status = targetEntry.getStatus();
        final var newEntry = dataMapper.mapObject(sourceEntry, targetEntry, PaymentEntry.class);
        if (newEntry.getStatus() == null) {
            newEntry.setStatus(status);
        }
        historyService.addHistory(newEntry, CommandType.UPDATE, context.authenticatedUser(), null);
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
