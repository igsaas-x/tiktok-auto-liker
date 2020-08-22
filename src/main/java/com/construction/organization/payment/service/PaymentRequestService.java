package com.construction.organization.payment.service;

import com.construction.organization.payment.domain.*;
import com.construction.organization.payment.repository.PaymentRequestRepository;
import com.construction.organization.payment.repository.StatusHistoryRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.user.authentication.domain.AppUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentRequestService {

    final EntityDataMapper dataMapper;
    final PaymentRequestRepository repository;
    final StatusHistoryRepository historyRepository;

    public PaymentRequest save(PaymentRequest paymentRequest) {
        return repository.save(paymentRequest);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PaymentRequest getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PaymentRequest.class, id));
    }

    public Page<PaymentRequest> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}