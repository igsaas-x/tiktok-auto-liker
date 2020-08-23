package com.construction.organization.payment.dto.mapper;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.dto.PaymentEntryDto;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentEntryMapper extends DtoMapper<PaymentEntry, PaymentEntryDto> {

    @Autowired
    private TaskRepository taskRepository;

    protected PaymentEntryMapper() {
        super(PaymentEntry.class, PaymentEntryDto.class);
    }

    @Override
    public PaymentEntry toEntity(PaymentEntryDto dto) {
        final var entry = super.toEntity(dto);
        if (dto.getTaskId() != null) {
            final var task = taskRepository.findById(dto.getTaskId()).orElseThrow(() -> new ResourceNotFoundException(Task.class, dto.getTaskId()));
            entry.setTask(task);
        }else {
            System.out.println("task is null");
        }
        return entry;
    }
}
