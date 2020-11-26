package com.construction.organization.payment.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.PaymentEntryStatus;
import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.repository.PaymentRequestRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentRequestService {

    final EntityDataMapper dataMapper;
    final PaymentRequestRepository repository;
    final StatusHistoryService historyService;
    final ApplicationSecurityContext context;

    public PaymentRequest save(PaymentRequest paymentRequest) {

        final var request = repository.save(paymentRequest);
        request.getEntries().forEach(entry -> {
            historyService.addHistory(entry, CommandType.CREATE, null, context.authenticatedUser(), null);
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

    public PaymentRequest addEntry(Long id, List<PaymentEntry> entries) {
        final var paymentRequest = getById(id);
        paymentRequest.setEntries(entries);
        return repository.save(paymentRequest);
    }

    @Cacheable("PageablePaymentRequest")
    public Page<PaymentRequest> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Cacheable("PaymentRequest")
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