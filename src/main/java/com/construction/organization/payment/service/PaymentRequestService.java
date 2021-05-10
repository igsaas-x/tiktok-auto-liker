package com.construction.organization.payment.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.PaymentEntryStatus;
import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.repository.PaymentRequestRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PaymentRequestService {

    final EntityDataMapper dataMapper;
    final PaymentRequestRepository repository;
    final StatusHistoryService historyService;
    final ApplicationSecurityContext context;
    final TaskRepository taskRepository;

    public PaymentRequest create(PaymentRequest paymentRequest) {
        final var request = repository.save(paymentRequest);
        request.getEntries().forEach(entry -> {
            // validate request amount
            var availableAmount = taskRepository.getAvailableAmount(entry.getTask().getId());
            if (entry.getRequestAmount().compareTo(availableAmount) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request amount cannot be greater than available amount");
            }
            // validate approve amount
            if (entry.getApprovedAmount() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Approve amount cannot be set on create request");
            }

            historyService.addHistory(
                    entry,
                    CommandType.CREATE,
                    null,
                    context.authenticatedUser(),
                    null,
                    null,
                    null);
        });
        return request;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PaymentRequest getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PaymentRequest.class, id));
    }

    public PaymentRequest addEntries(Long id, List<PaymentEntry> entries) {
        final var paymentRequest = getById(id);
        paymentRequest.getEntries().addAll(entries);
        return repository.save(paymentRequest);
    }

    public PaymentRequest update(Long id, PaymentRequest source) {
        final var target = getById(id);
        final var paymentRequest = dataMapper.mapObject(source, target, PaymentRequest.class);
        return repository.save(paymentRequest);
    }

    public Page<PaymentRequest> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<PaymentRequest> getAll() {
        return repository.findAll();
    }

    public Page<PaymentRequest> findPendingFor(final CommandType pendingFor, Pageable pageable) {
        switch (pendingFor) {
            case SUBMIT:
                return repository.getPendingPaymentRequest(PaymentEntryStatus.OPEN, pageable);
            case VERIFY:
                return repository.getPendingPaymentRequest(PaymentEntryStatus.SUBMITTED, pageable);
            case CONFIRM:
                return repository.getPendingPaymentRequest(PaymentEntryStatus.VERIFIED, pageable);
            case REVIEW:
                return repository.getPendingPaymentRequest(PaymentEntryStatus.CONFIRMED, pageable);
            case APPROVE:
                return repository.getPendingPaymentRequest(PaymentEntryStatus.REVIEWED, pageable);
            case CASH_OUT:
                return repository.getPendingPaymentRequest(PaymentEntryStatus.APPROVED, pageable);
        }
        return Page.empty();
    }
}