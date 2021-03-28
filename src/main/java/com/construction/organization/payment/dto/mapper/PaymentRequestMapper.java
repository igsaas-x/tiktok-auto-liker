package com.construction.organization.payment.dto.mapper;

import com.construction.feature.task.dto.mapper.TaskMapper;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.dto.PaymentEntryDto;
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
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public PaymentRequest toEntity(PaymentRequestDto dto) {
        final var request = super.toEntity(dto);
        if (dto.getEntries() != null) {
            final var entries = dto.getEntries()
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

    @Override
    public PaymentRequestDto apply(PaymentRequest paymentRequest) {
        final var dto = super.apply(paymentRequest);
        final var entriesDto = paymentRequest.getEntries().stream()
                .map(this::getDto)
                .collect(Collectors.toList());
        dto.setEntries(entriesDto);
        return dto;
    }

    private PaymentEntryDto getDto(final PaymentEntry paymentEntry) {
        final var paymentEntryDto = paymentEntryMapper.apply(paymentEntry);
        final var taskDto = taskMapper.apply(paymentEntry.getTask());
        paymentEntryDto.setTaskDto(taskDto);
        return paymentEntryDto;
    }
}
