package com.construction.feature.task.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.task.domain.TaskTemplate;
import com.construction.feature.task.repository.TaskTemplateRepository;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.EntityValidator;
import com.construction.user.authorization.domain.ActionName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskTemplateService {

    private final TaskTemplateRepository repository;
    private final EntityDataMapper dataMapper;
    private final EntityValidator<TaskTemplate> validator;
    private final ApplicationSecurityContext context;

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

    public TaskTemplate verify(final Long id) {
        final var tt = getById(id);
        validator.validateStatus(tt, ActionName.VERIFY);
        tt.setStatus(ObjectStatus.VERIFIED);
        tt.setVerifiedAt(LocalDateTime.now());
        tt.setVerifiedBy(context.authenticatedUser());
        return repository.save(tt);
    }

    public TaskTemplate approve(final Long id) {
        final var tt = getById(id);
        validator.validateStatus(tt, ActionName.APPROVE);
        tt.setStatus(ObjectStatus.APPROVED);
        tt.setApprovedAt(LocalDateTime.now());
        tt.setApprovedBy(context.authenticatedUser());
        return repository.save(tt);
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
