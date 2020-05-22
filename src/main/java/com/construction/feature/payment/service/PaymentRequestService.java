package com.construction.feature.payment.service;

import com.construction.feature.payment.domain.PaymentRequest;
import com.construction.feature.payment.repository.PaymentRequestRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PaymentRequestService {

    @Autowired
    private EntityDataMapper dataMapper;
    @Autowired
    private PaymentRequestRepository repository;

    public PaymentRequest save(PaymentRequest dto) {
        return repository.save(dto);
    }

    public void save(List<PaymentRequest> dtos) {
        repository.saveAll(dtos);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PaymentRequest getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PaymentRequest.class, id));
    }

    public List<PaymentRequest> findAll() {
        return repository.findAll();
    }

    public Page<PaymentRequest> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public PaymentRequest updateById(Long id, PaymentRequest source) {
        var target = getById(id);
        target = dataMapper.mapObject(source, target, PaymentRequest.class);
        return repository.save(target);
    }
}