package com.construction.organization.payment.dto;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.repository.PaymentRequestRepository;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.repository.SubConstructorRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEntryMapper {

    private final PaymentRequestRepository requestRepository;
    private final SubConstructorRepository subConstructorRepository;
    private final TaskRepository taskRepository;

    public PaymentEntryDto toDto(PaymentEntry paymentEntry) {
        return new PaymentEntryDto()
                .setApprovedAmount(paymentEntry.getApprovedAmount())
                .setDescription(paymentEntry.getDescription())
                .setRequestAmount(paymentEntry.getRequestAmount())
                .setSubConstructorId(paymentEntry.getSubConstructor().getId())
                .setTaskName(paymentEntry.getTask().getName())
                .setTaskId(paymentEntry.getTask().getId());
    }

    public PaymentEntry toEntity(PaymentEntryDto paymentEntryDto, final PaymentRequest paymentRequest) {
        final var entry = new PaymentEntry()
                .setApprovedAmount(paymentEntryDto.getApprovedAmount())
                .setDescription(paymentEntryDto.getDescription())
                .setPaymentRequest(paymentRequest)
                .setRequestAmount(paymentEntryDto.getRequestAmount());
        if (paymentEntryDto.getTaskId() != null) {
            final var task = taskRepository.findById(paymentEntryDto.getTaskId())
                    .orElseThrow(() -> new ResourceNotFoundException(Task.class, paymentEntryDto.getTaskId()));
            entry.setTask(task);
        }
        if (paymentEntryDto.getSubConstructorId() != null) {
            final var subConstructor = subConstructorRepository.findById(paymentEntryDto.getSubConstructorId())
                    .orElseThrow(() -> new ResourceNotFoundException(SubConstructor.class, paymentEntryDto.getSubConstructorId()));
            entry.setSubConstructor(subConstructor);
        }
        return entry;
    }
}
