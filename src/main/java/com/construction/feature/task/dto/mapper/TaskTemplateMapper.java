package com.construction.feature.task.dto.mapper;

import com.construction.feature.task.domain.TaskTemplate;
import com.construction.feature.task.dto.TaskTemplateDto;
import com.construction.feature.task.repository.TaskTemplateRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskTemplateMapper extends DtoMapper<TaskTemplate, TaskTemplateDto> {

    private final TaskTemplateRepository taskTemplateRepository;

    public TaskTemplateMapper(TaskTemplateRepository taskTemplateRepository) {
        super(TaskTemplate.class, TaskTemplateDto.class);
        this.taskTemplateRepository = taskTemplateRepository;
    }

    @Override
    public TaskTemplate toEntity(TaskTemplateDto taskTemplateDto) {

        final var taskTemplate = modelMapper.map(taskTemplateDto, TaskTemplate.class);

        if (taskTemplateDto.getParentId() != null) {
            taskTemplateRepository.findById(taskTemplateDto.getParentId()).ifPresentOrElse(
                    taskTemplate::setParent,
                    () -> new ResourceNotFoundException(TaskTemplate.class, taskTemplateDto.getParentId())
            );
        }
        return taskTemplate;
    }
}
