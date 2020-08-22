package com.construction.feature.task.service;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.domain.TaskSubConstructorAssign;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.feature.task.repository.TaskSubConstructorAssignRepository;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.repository.SubConstructorRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskSubConstructAssignService {

    private final TaskSubConstructorAssignRepository repository;
    private final TaskRepository taskRepository;
    private final SubConstructorRepository subConstructorRepository;

    public TaskSubConstructorAssign assign(final Long taskId, final Long subId) {
        final var task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException(Task.class, taskId));
        final var sub = subConstructorRepository.findById(subId).orElseThrow(() -> new ResourceNotFoundException(SubConstructor.class, subId));
        final var assign = new TaskSubConstructorAssign()
                .setCreatedAt(LocalDateTime.now())
                .setTask(task)
                .setSubConstructor(sub);
        return repository.save(assign);
    }
}
