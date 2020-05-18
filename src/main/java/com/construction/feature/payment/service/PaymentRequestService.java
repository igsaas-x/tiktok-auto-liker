package com.construction.feature.payment.service;

import com.construction.feature.payment.dto.PaymentRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PaymentRequestService {
    PaymentRequestDTO save(PaymentRequestDTO dto);

    void save(List<PaymentRequestDTO> dtos);

    void deleteById(Long id);

    Optional<PaymentRequestDTO> findById(Long id);

    List<PaymentRequestDTO> findAll();

    Page<PaymentRequestDTO> findAll(Pageable pageable);

    PaymentRequestDTO updateById(Long id);
}