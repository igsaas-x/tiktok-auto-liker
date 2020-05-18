package com.construction.feature.project.services;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.project.domain.Project;
import com.construction.feature.project.repositories.ProjectAssignRepository;
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
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;
    @Autowired
    private ProjectAssignRepository assignRepository;
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

    public Project update(Long id, Project project) {
        var target = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Project.class, id));
        var user = context.authenticatedUser();
        if (target.getCreatedBy().equals(user)) {
            if (target.getStatus().equals(ObjectStatus.VERIFIED) || target.getStatus().equals(ObjectStatus.APPROVED)) {
                throw new RuntimeException("your project has been verified or approved, please delete and re-create request");
            }
        }
        target = mapper.mapObject(project, target, Project.class);
        return repository.save(target);
    }

    public Page<Project> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Project> getPendingProject(Pageable pageable) {
        var user = context.authenticatedUser();
        var projects = repository.findUserPendingProject(user.getId(), pageable);
        return projects.stream()
                .filter(project -> project.getStatus().equals(ObjectStatus.OPEN) || project.getStatus().equals(ObjectStatus.VERIFIED))
                .filter(project -> !user.equals(project.getVerifiedBy()))
                .collect(Collectors.toList());
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
