package com.construction.organization.payment.dto.mapper;

import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.dto.PaymentRequestDto;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.repository.SubConstructorRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PaymentRequestMapper extends DtoMapper<PaymentRequest, PaymentRequestDto> {

    @Autowired
    private SubConstructorRepository subConstructorRepository;
    @Autowired
    private PaymentEntryMapper paymentEntryMapper;

    protected PaymentRequestMapper() {
        super(PaymentRequest.class, PaymentRequestDto.class);
    }

    @Override
    public PaymentRequest toEntity(PaymentRequestDto dto) {
        final var request = super.toEntity(dto);
        if (dto.getPaymentEntries() != null) {
            final var entries = dto.getPaymentEntries()
                    .stream()
                    .map(paymentEntryMapper::toEntity)
                    .collect(Collectors.toList());
            request.setEntries(entries);
        }
        if (dto.getSubConstructorId() != null) {
            final var sub = subConstructorRepository.findById(dto.getSubConstructorId())
                    .orElseThrow(() -> new ResourceNotFoundException(SubConstructor.class, dto.getSubConstructorId()));
            request.setSubConstructor(sub);
        }
        return request;
    }
}
