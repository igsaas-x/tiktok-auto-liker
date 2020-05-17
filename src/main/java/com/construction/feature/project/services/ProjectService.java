package com.construction.feature.project.services;

import com.construction.feature.project.domain.Project;
import com.construction.feature.project.repositories.ProjectAssignRepository;
import com.construction.feature.project.repositories.ProjectRepository;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.SFWhere;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;
    @Autowired
    private ProjectAssignRepository assignRepository;
    @Autowired
    private EntityDataMapper mapper;

    public ResponseEntity<Object> search(Project project, Pageable pageable) {
        Page<Project> all = repository.findAll(SFWhere.and(project)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    public Project create(Project project) {
        return repository.save(project);
    }

    public Project update(Long id, Project project) {
        var target = repository.findById(id).orElseThrow();
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
        repository.deleteById(id);
    }
}
