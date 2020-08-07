package com.construction.organization.payment.dto;

import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.persistence.mapper.DtoMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Component
public class PaymentRequestDtoMapper implements DtoMapper<PaymentRequestDto, PaymentRequest> {

    private final PaymentEntryMapper entryMapper;

    @Override
    public PaymentRequest toEntity(PaymentRequestDto dto) {
        return new PaymentRequest();
    }

    @Override
    public PaymentRequestDto toDto(PaymentRequest entity) {
        final var entries = entity.getEntries()
                .stream()
                .map(entryMapper::toDto)
                .collect(Collectors.toList());
        return new PaymentRequestDto()
                .setId(entity.getId())
                .setCreatedAt(entity.getCreatedAt())
                .setCreatedBy(entity.getCreatedBy().getUserName())
                .setUpdatedAt(entity.getUpdatedAt())
                .setUpdatedBy(entity.getUpdatedBy().getUserName())
                .setStatus(entity.getStatus())
                .setEntries(entries);
    }
}
