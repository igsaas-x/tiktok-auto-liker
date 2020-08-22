package com.construction.organization.payment.service;

import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.PaymentRequestStatus;
import com.construction.organization.payment.domain.StatusHistory;
import com.construction.organization.payment.repository.PaymentEntryRepository;
import com.construction.organization.payment.repository.StatusHistoryRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.user.authentication.domain.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentEntryService {

    private final PaymentEntryRepository repository;
    private final EntityDataMapper dataMapper;
    private final StatusHistoryRepository historyRepository;

    public PaymentEntry getById(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PaymentEntry.class, id));
    }

    public List<PaymentEntry> getByAllId(final List<Long> ids) {
        if (ids.size() > 50) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "too many content");
        }
        return repository.findByIdIn(ids);
    }

    public Page<PaymentEntry> getBySubConstructorId(final Long id, Pageable pageable) {
        return repository.findAllByPaymentRequestSubConstructorId(id, pageable);
    }

    public List<PaymentEntry> getByPaymentRequestId(final Long id) {
        return repository.findAllByPaymentRequestId(id);
    }

    public Page<PaymentEntry> findPendingFor(final CommandType pendingFor, Pageable pageable) {
        switch (pendingFor) {
            case SUBMIT:
                return repository.findAllByStatus(PaymentRequestStatus.OPEN, pageable);
            case VERIFY:
                return repository.findAllByStatus(PaymentRequestStatus.SUBMITTED, pageable);
            case CONFIRM:
                return repository.findAllByStatus(PaymentRequestStatus.VERIFIED, pageable);
            case REVIEW:
                return repository.findAllByStatus(PaymentRequestStatus.CONFIRMED, pageable);
            case APPROVE:
                return repository.findAllByStatus(PaymentRequestStatus.REVIEWED, pageable);
            case CASH_OUT:
                return repository.findAllByStatus(PaymentRequestStatus.APPROVED, pageable);
        }
        return Page.empty();
    }

    public PaymentEntry handleCommand(final PaymentEntry paymentEntry, final CommandType command, final AppUser user, final String comment) {
        switch (command) {
            case SUBMIT:
                if (!paymentEntry.getStatus().equals(PaymentRequestStatus.OPEN)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in open status");
                }
                paymentEntry.setStatus(PaymentRequestStatus.SUBMITTED);
                break;
            case VERIFY:
                if (!paymentEntry.getStatus().equals(PaymentRequestStatus.SUBMITTED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in submitted status");
                }
                paymentEntry.setStatus(PaymentRequestStatus.VERIFIED);
                break;
            case CONFIRM:
                if (!paymentEntry.getStatus().equals(PaymentRequestStatus.VERIFIED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in verified status");
                }
                paymentEntry.setStatus(PaymentRequestStatus.CONFIRMED);
                break;
            case REVIEW:
                if (!paymentEntry.getStatus().equals(PaymentRequestStatus.CONFIRMED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in confirmed status");
                }
                paymentEntry.setStatus(PaymentRequestStatus.REVIEWED);
                break;
            case APPROVE:
                if (!paymentEntry.getStatus().equals(PaymentRequestStatus.REVIEWED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in reviewed status");
                }
                paymentEntry.setStatus(PaymentRequestStatus.APPROVED);
                break;
            case CASH_OUT:
                if (!paymentEntry.getStatus().equals(PaymentRequestStatus.APPROVED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in approved status");
                }
                paymentEntry.setStatus(PaymentRequestStatus.PAID);
                break;
            case REJECT:
                paymentEntry.setStatus(PaymentRequestStatus.OPEN);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "command not found");
        }
        addHistory(paymentEntry, command, user, comment);
        return repository.save(paymentEntry);
    }

    private void addHistory(PaymentEntry entry, CommandType commandType, AppUser doneBy, String comment) {
        var history = new StatusHistory()
                .setPaymentEntry(entry)
                .setCommandType(commandType)
                .setComment(comment);
        historyRepository.save(history);
    }

    public Page<PaymentEntry> getAllPending(final Pageable pageable) {
        return repository.findAllByStatusNot(PaymentRequestStatus.APPROVED, pageable);
    }

    public PaymentEntry update(final Long id, final PaymentEntry sourceEntry) {
        final var targetEntry = getById(id);
        final var newEntry = dataMapper.mapObject(sourceEntry, targetEntry, PaymentEntry.class);
        return repository.save(newEntry);
    }

    public boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }
}
