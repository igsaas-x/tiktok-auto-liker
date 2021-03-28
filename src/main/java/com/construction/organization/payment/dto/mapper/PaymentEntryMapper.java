package com.construction.organization.payment.dto.mapper;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.dto.mapper.TaskMapper;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.dto.PaymentEntryDto;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.mapper.DtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentEntryMapper extends DtoMapper<PaymentEntry, PaymentEntryDto> {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public PaymentEntry toEntity(PaymentEntryDto dto) {
        final var entry = super.toEntity(dto);
        if (dto.getTaskId() != null) {
            final var task = taskRepository.findById(dto.getTaskId()).orElseThrow(() -> new ResourceNotFoundException(Task.class, dto.getTaskId()));
            entry.setTask(task);
        } else {
            log.error("task is null");
        }
        return entry;
    }

    @Override
    public PaymentEntryDto apply(PaymentEntry paymentEntry) {
        final var dto = super.apply(paymentEntry);
        final var taskDto = taskMapper.apply(paymentEntry.getTask());
        dto.setTaskDto(taskDto);
        return dto;
    }
}
