package com.construction.organization.subconstructor.services;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.FilterType;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.repository.SubConstructorRepository;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.ObjectStatusValidator;
import com.construction.persistence.utils.SFWhere;
import com.construction.user.authorization.domain.ActionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubConstructorService {

    @Autowired
    private SubConstructorRepository repository;
    @Autowired
    private EntityDataMapper dataMapper;
    @Autowired
    private ObjectStatusValidator<SubConstructor> validator;
    @Autowired
    private ApplicationSecurityContext context;

    public SubConstructor create(final SubConstructor subConstructor) {
        return repository.save(subConstructor);
    }

    public SubConstructor getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SubConstructor.class, id));
    }

    public ResponseEntity<Object> search(SubConstructor subConstructor, Pageable pageable) {
        Page<SubConstructor> all = repository.findAll(SFWhere.and(subConstructor)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    public List<SubConstructor> getAll() {
        return repository.findAll();
    }

    public Page<SubConstructor> getAll(Pageable pageable, FilterType filterType) {
        switch (filterType) {
            case OWNED:
                return repository.findAllByCreatedById(context.authenticatedUser().getId(), pageable);
            case PENDING_FOR_VERIFY:
                return repository.findAllByStatus(ObjectStatus.OPEN, pageable);
            case PENDING_FOR_APPROVE:
                return repository.findAllByStatus(ObjectStatus.VERIFIED, pageable);
            case ALL:
                return repository.findAll(pageable);
            default:
                return null;
        }
    }

    public void delete(Long id) {
        var target = getById(id);
        repository.delete(target);
    }

    public List<SubConstructor> getPendingForVerify() {
        return repository.findAllByStatus(ObjectStatus.OPEN);
    }

    public List<SubConstructor> getPendingForApprove() {
        return repository.findAllByStatus(ObjectStatus.VERIFIED);
    }

    public SubConstructor update(Long id, SubConstructor source) {
        var target = getById(id);
        validator.validateStatus(target, ActionName.UPDATE);
        target = dataMapper.mapObject(source, target, SubConstructor.class);
        return repository.save(target);
    }

    public SubConstructor verify(Long id) {
        var project = getById(id);
        validator.validateStatus(project, ActionName.VERIFY);
        project.setStatus(ObjectStatus.VERIFIED);
        project.setVerifiedBy(context.authenticatedUser());
        project.setVerifiedAt(LocalDateTime.now());
        return repository.save(project);
    }

    public SubConstructor approve(Long id) {
        var project = getById(id);
        validator.validateStatus(project, ActionName.APPROVE);
        project.setStatus(ObjectStatus.APPROVED);
        project.setVerifiedBy(context.authenticatedUser());
        project.setVerifiedAt(LocalDateTime.now());
        return repository.save(project);
    }
}
