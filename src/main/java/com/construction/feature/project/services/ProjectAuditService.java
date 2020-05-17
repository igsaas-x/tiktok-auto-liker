package com.construction.feature.project.services;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.project.domain.Project;
import com.construction.feature.project.domain.ProjectAudit;
import com.construction.feature.project.repositories.ProjectAuditRepository;
import com.construction.feature.project.repositories.ProjectRepository;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


@Service
public class ProjectAuditService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectAuditRepository repository;
    @Autowired
    private ApplicationSecurityContext context;

    public Page<ProjectAudit> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public ProjectAudit getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public ProjectAudit verify(Long id) {
        var projectAudit = getById(id);
        if (!projectAudit.getStatus().equals(ObjectStatus.OPEN)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "project is not on open status");
        }
        projectAudit.setStatus(ObjectStatus.VERIFIED);
        projectAudit.setVerifiedBy(context.authenticatedUser());
        projectAudit.setVerifiedAt(LocalDateTime.now());
        return repository.save(projectAudit);
    }

    public Object approve(Long id) {
        var projectAudit = getById(id);
        if (!projectAudit.getStatus().equals(ObjectStatus.VERIFIED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "project is not on verify status");
        }
        projectAudit.setStatus(ObjectStatus.APPROVED);
        projectAudit.setVerifiedBy(context.authenticatedUser());
        projectAudit.setVerifiedAt(LocalDateTime.now());
        repository.save(projectAudit);
        var project = projectRepository.findById(projectAudit.getProjectId()).orElseThrow(
                () -> new ResourceNotFoundException(Project.class, projectAudit.getProjectId()));
        project.setCode(projectAudit.getCode());
        project.setObjectName(projectAudit.getObjectName());
        project.setObjectType(projectAudit.getObjectType());
        project.setDescription(projectAudit.getDescription());
        return projectRepository.save(project);
    }
}
