package com.construction.organization.payment.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
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
                if (!paymentEntry.getStatus().equals(PaymentEntryStatus.OPEN)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in open status");
                }
                paymentEntry.setStatus(PaymentEntryStatus.SUBMITTED);
                break;
            case VERIFY:
                if (!paymentEntry.getStatus().equals(PaymentEntryStatus.SUBMITTED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in submitted status");
                }
                paymentEntry.setStatus(PaymentEntryStatus.VERIFIED);
                break;
            case CONFIRM:
                if (!paymentEntry.getStatus().equals(PaymentEntryStatus.VERIFIED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in verified status");
                }
                paymentEntry.setStatus(PaymentEntryStatus.CONFIRMED);
                break;
            case REVIEW:
                if (!paymentEntry.getStatus().equals(PaymentEntryStatus.CONFIRMED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in confirmed status");
                }
                paymentEntry.setStatus(PaymentEntryStatus.REVIEWED);
                break;
            case APPROVE:
                if (!paymentEntry.getStatus().equals(PaymentEntryStatus.REVIEWED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in reviewed status");
                }
                paymentEntry.setStatus(PaymentEntryStatus.APPROVED);
                break;
            case CASH_OUT:
                if (!paymentEntry.getStatus().equals(PaymentEntryStatus.APPROVED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in approved status");
                }
                paymentEntry.setStatus(PaymentEntryStatus.PAID);
                break;
            case REJECT:
                if (OPEN.equals(paymentEntry.getStatus())
                        || APPROVED.equals(paymentEntry.getStatus())
                        || PAID.equals(paymentEntry.getStatus())) {
                    throw new RuntimeException("status cannot be rejected");
                }
                paymentEntry.setStatus(PaymentEntryStatus.OPEN);
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
        final var newEntry = dataMapper.mapObject(sourceEntry, targetEntry, PaymentEntry.class);
        historyService.addHistory(newEntry, CommandType.UPDATE, context.authenticatedUser(), null);
        return repository.save(newEntry);
    }

    public boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }
}
