package com.construction.feature.task.service;

import com.construction.feature.task.domain.TaskTemplate;
import com.construction.feature.task.repository.TaskTemplateRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskTemplateService {

    private final TaskTemplateRepository repository;
    private final EntityDataMapper dataMapper;

    public TaskTemplate getById(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(TaskTemplate.class, id));
    }

    public TaskTemplate create(final TaskTemplate taskTemplate) {
        return repository.save(taskTemplate);
    }

    public TaskTemplate update(final Long id, final TaskTemplate source) {
        final var target = getById(id);
        final var template = dataMapper.mapObject(source, target, TaskTemplate.class);
        return repository.save(template);
    }

    public boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    public Page<TaskTemplate> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public TaskTemplate getByCode(String code) {
        return repository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException(TaskTemplate.class, code));
    }

    public List<TaskTemplate> getByParentId(final Long id) {
        return repository.findAllByParentId(id);
    }
}
