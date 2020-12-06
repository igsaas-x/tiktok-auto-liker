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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskSubConstructAssignService {

    private final TaskSubConstructorAssignRepository repository;
    private final TaskRepository taskRepository;
    private final SubConstructorRepository subConstructorRepository;

    public List<TaskSubConstructorAssign> assign(final Long taskId, final List<Long> subIds) {
        final var task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException(Task.class, taskId));
        final var subs = subConstructorRepository.findAllById(subIds);

        final var taskSubAssigns = repository.findAllByTaskId(taskId);
        repository.deleteAll(taskSubAssigns);

        final var assign = subs.stream()
                .map(sub -> build(task, sub))
                .collect(Collectors.toList());

        return repository.saveAll(assign);
    }

    private TaskSubConstructorAssign build(final Task task, final SubConstructor subConstructor) {
        return new TaskSubConstructorAssign()
                .setCreatedAt(LocalDateTime.now())
                .setTask(task)
                .setSubConstructor(subConstructor);
    }
}
