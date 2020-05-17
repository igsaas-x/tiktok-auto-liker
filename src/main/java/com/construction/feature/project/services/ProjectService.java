package com.construction.feature.project.services;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.project.domain.Project;
import com.construction.feature.project.domain.ProjectAudit;
import com.construction.feature.project.repositories.ProjectAssignRepository;
import com.construction.feature.project.repositories.ProjectAuditRepository;
import com.construction.feature.project.repositories.ProjectRepository;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.SFWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;
    @Autowired
    private ProjectAssignRepository assignRepository;
    @Autowired
    private ProjectAuditRepository auditRepository;
    @Autowired
    private EntityDataMapper mapper;
    @Autowired
    private ApplicationSecurityContext context;

    public ResponseEntity<Object> search(Project project, Pageable pageable) {
        Page<Project> all = repository.findAll(SFWhere.and(project)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    public Project create(Project project) {
        return repository.save(project);
    }

    public Object update(Long id, Project project) {
        var target = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Project.class, id));
        if(target.getStatus().equals(ObjectStatus.VERIFIED) || target.getStatus().equals(ObjectStatus.APPROVED)){
            var projectAudit = new ProjectAudit()
                    .setProjectId(target.getId())
                    .setCode(target.getCode())
                    .setObjectType(target.getObjectType())
                    .setObjectName(target.getObjectName())
                    .setDescription(target.getDescription());
            return auditRepository.save(projectAudit);
        }
        target = mapper.mapObject(project, target, Project.class);
        return repository.save(target);
    }

    public Page<Project> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Project getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void delete(Long id) {
        var project = getById(id);
        repository.delete(project);
    }

    public Project verify(Long id) {
        var project = getById(id);
        if (!project.getStatus().equals(ObjectStatus.OPEN)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "project is not on open status");
        }
        project.setStatus(ObjectStatus.VERIFIED);
        project.setVerifiedBy(context.authenticatedUser());
        project.setVerifiedAt(LocalDateTime.now());
        return repository.save(project);
    }

    public Project approve(Long id) {
        var project = getById(id);
        if (!project.getStatus().equals(ObjectStatus.VERIFIED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "project is not on verify status");
        }
        project.setStatus(ObjectStatus.APPROVED);
        project.setVerifiedBy(context.authenticatedUser());
        project.setVerifiedAt(LocalDateTime.now());
        return repository.save(project);
    }
}
