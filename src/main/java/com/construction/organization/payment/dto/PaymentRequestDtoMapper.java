package com.construction.organization.payment.dto;

import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.feature.task.service.TaskService;
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
                .setSubConstructor(constructorService.getById(dto.getSubConstructorId()));
    }
}
