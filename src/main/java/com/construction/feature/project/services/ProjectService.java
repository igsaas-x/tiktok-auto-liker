package com.construction.feature.project.services;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.project.domain.Project;
import com.construction.feature.project.domain.ProjectAssign;
import com.construction.feature.project.repositories.ProjectAssignRepository;
import com.construction.feature.project.repositories.ProjectRepository;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.ObjectStatusValidator;
import com.construction.persistence.utils.SFWhere;
import com.construction.user.authentication.service.AppUserService;
import com.construction.user.authorization.domain.ActionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository repository;
    @Autowired
    private ProjectAssignRepository assignRepository;
    @Autowired
    private EntityDataMapper mapper;
    @Autowired
    private ApplicationSecurityContext context;
    @Autowired
    private ObjectStatusValidator<Project> validator;
    @Autowired
    private AppUserService userService;

    public ResponseEntity<Object> search(Project project, Pageable pageable) {
        Page<Project> all = repository.findAll(SFWhere.and(project)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    public Project create(Project project) {
        return repository.save(project);
    }

    public Project update(Long id, Project project) {
        var target = getById(id);
        validator.validateStatus(target, ActionName.UPDATE);
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
        validator.validateStatus(project, ActionName.VERIFY);
        project.setStatus(ObjectStatus.VERIFIED);
        project.setVerifiedBy(context.authenticatedUser());
        project.setVerifiedAt(LocalDateTime.now());
        return repository.save(project);
    }

    public Project approve(Long id) {
        var project = getById(id);
        validator.validateStatus(project, ActionName.APPROVE);
        project.setStatus(ObjectStatus.APPROVED);
        project.setVerifiedBy(context.authenticatedUser());
        project.setVerifiedAt(LocalDateTime.now());
        return repository.save(project);
    }

    public ProjectAssign assign(Long id, Long userId) {
        var project = getById(id);
        var user = userService.getById(userId);
        var projectAssign = new ProjectAssign();
        projectAssign.setProject(project);
        projectAssign.setAppUser(user);
        return assignRepository.save(projectAssign);
    }

    public boolean unAssign(Long id, Long userId) {
        var project = getById(id);
        var user = userService.getById(userId);
        var projectAssign = assignRepository.findByProjectAndAppUser(project, user).orElseThrow();
        assignRepository.delete(projectAssign);
        return true;
    }
}
