package com.construction.feature.payment.mapper.impl;

import com.construction.feature.payment.domain.PaymentRequest;
import com.construction.feature.payment.dto.PaymentRequestDTO;
import com.construction.feature.payment.mapper.PaymentRequestMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentRequestMapperImpl implements PaymentRequestMapper {
    @Override
    public PaymentRequest toEntity(PaymentRequestDTO dto) {
        PaymentRequest entity = new PaymentRequest();
        entity.setReviewedBy(dto.getReviewedBy());
        entity.setReviewedAt(dto.getReviewedAt());
        entity.setTask(dto.getTask());
        entity.setDescription(dto.getDescription());
        entity.setReceiver(dto.getReceiver());
        return entity;
    }

    @Override
    public PaymentRequestDTO toDto(PaymentRequest entity) {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setReviewedBy(entity.getReviewedBy());
        dto.setReviewedAt(entity.getReviewedAt());
        dto.setTask(entity.getTask());
        dto.setDescription(entity.getDescription());
        dto.setReceiver(entity.getReceiver());
        return dto;
    }

    @Override
    public List<PaymentRequest> toEntityList(List<PaymentRequestDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<PaymentRequestDTO> toDtoList(List<PaymentRequest> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}