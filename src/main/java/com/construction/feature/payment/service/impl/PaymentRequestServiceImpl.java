package com.construction.feature.payment.service.impl;

import com.construction.feature.payment.dao.PaymentRequestRepository;
import com.construction.feature.payment.domain.PaymentRequest;
import com.construction.feature.payment.dto.PaymentRequestDTO;
import com.construction.feature.payment.mapper.PaymentRequestMapper;
import com.construction.feature.payment.service.PaymentRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentRequestServiceImpl implements PaymentRequestService {
    private final PaymentRequestMapper mapper;
    private final PaymentRequestRepository repository;

    public PaymentRequestServiceImpl(PaymentRequestMapper mapper, PaymentRequestRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public PaymentRequestDTO save(PaymentRequestDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void save(List<PaymentRequestDTO> dtos) {
        repository.saveAll(mapper.toEntityList(dtos));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<PaymentRequestDTO> findById(Long id) {
        Optional<PaymentRequest> entityOptional = repository.findById(id);
        return entityOptional.map(entity -> Optional.ofNullable(mapper.toDto(entity))).orElse(null);
    }

    @Override
    public List<PaymentRequestDTO> findAll() {
        return mapper.toDtoList((List<PaymentRequest>) repository.findAll());
    }

    @Override
    public Page<PaymentRequestDTO> findAll(Pageable pageable) {
        Page<PaymentRequest> entityPage = repository.findAll(pageable);
        List<PaymentRequestDTO> dtos = mapper.toDtoList(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public PaymentRequestDTO updateById(Long id) {
        Optional<PaymentRequestDTO> optionalDto = findById(id);
        return optionalDto.map(this::save).orElse(null);
    }
}