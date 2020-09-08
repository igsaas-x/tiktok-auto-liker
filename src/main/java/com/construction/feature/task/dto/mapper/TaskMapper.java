package com.construction.feature.task.dto.mapper;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.domain.TaskSubConstructorAssign;
import com.construction.feature.task.dto.TaskDto;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.feature.task.repository.TaskSubConstructorAssignRepository;
import com.construction.feature.task.repository.TaskTemplateRepository;
import com.construction.organization.subconstructor.data.SubConstructorMapper;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskMapper extends DtoMapper<Task, TaskDto> {

    @Autowired
    private TaskSubConstructorAssignRepository subConstructorAssignRepository;
    @Autowired
    private SubConstructorMapper subConstructorMapper;
    @Autowired
    private TaskTemplateRepository taskTemplateRepository;
    @Autowired
    private TaskRepository taskRepository;

    protected TaskMapper() {
        super(Task.class, TaskDto.class);
    }

    @Override
    public TaskDto apply(Task task) {
        final var taskDto = super.apply(task);
        final var subConstructorsDto = subConstructorAssignRepository
                .findAllByTaskId(taskDto.getId())
                .stream()
                .map(TaskSubConstructorAssign::getSubConstructor)
                .map(subConstructorMapper)
                .collect(Collectors.toList());
        taskDto.setSubConstructors(subConstructorsDto);
        return taskDto;
    }

    public TaskDto apply(Task task, final Long subConstructorId) {
        final var taskDto = super.apply(task);
        final var subConstructorsDto = subConstructorAssignRepository
                .findAllByTaskId(taskDto.getId())
                .stream()
                .map(TaskSubConstructorAssign::getSubConstructor)
                .map(subConstructorMapper)
                .collect(Collectors.toList());
        taskDto.setSubConstructors(subConstructorsDto);
        final var paidAmount = taskRepository.getPaidAmount(subConstructorId, task.getId());
        final var availableAmount = taskRepository.getAvailableAmount(task.getId());
        taskDto.setPaidAmount(paidAmount)
                .setAvailableAmount(availableAmount);
        return taskDto;
    }

    @Override
    public Task toEntity(TaskDto taskDto) {
        final var task = super.toEntity(taskDto);
        final var taskTemplate = taskTemplateRepository.findById(taskDto.getTaskTemplateId()).orElseThrow(
                () -> new ResourceNotFoundException(Task.class, taskDto.getTaskTemplateId()));
        task.setTaskTemplate(taskTemplate);
        return task;
    }
}
