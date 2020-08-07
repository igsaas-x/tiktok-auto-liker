package com.construction.organization.payment.dto;

import com.construction.feature.task.service.TaskService;
import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.subconstructor.services.SubConstructorService;
import com.construction.persistence.mapper.DtoMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Component
public class PaymentRequestDtoMapper implements DtoMapper<PaymentRequestDto, PaymentRequest> {

    final TaskService taskService;
    final SubConstructorService constructorService;

    @Override
    public PaymentRequest toEntity(PaymentRequestDto dto) {
        return new PaymentRequest()
                .setTask(taskService.getById(dto.getTaskId()))
                .setDescription(dto.getDescription())
                .setRequestAmount(dto.getRequestAmount())
                .setApprovedAmount(dto.getApprovedAmount())
                .setSubConstructor(constructorService.getById(dto.getSubConstructorId()));
    }

    @Override
    public PaymentRequestDto toDto(PaymentRequest entity) {
        var payment = new PaymentRequestDto()
                .setId(entity.getId())
                .setDescription(entity.getDescription())
                .setRequestAmount(entity.getRequestAmount())
                .setApprovedAmount(entity.getApprovedAmount())
                .setCreatedAt(entity.getCreatedAt())
                .setUpdatedAt(entity.getUpdatedAt())
                .setCreatedBy(entity.getCreatedBy() == null ? null : entity.getCreatedBy().getUserName())
                .setUpdatedBy(entity.getUpdatedBy() == null ? null : entity.getUpdatedBy().getUserName());
        if (entity.getTask() != null) {
            payment.setTaskId(entity.getTask().getId());
            payment.setTaskName(entity.getTask().getName());
        }
        if (entity.getSubConstructor() != null) {
            payment.setSubConstructorId(entity.getSubConstructor().getId());
        }
        return payment;
    }
}
