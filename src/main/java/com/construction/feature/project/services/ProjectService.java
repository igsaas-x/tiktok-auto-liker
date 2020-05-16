package com.construction.feature.project.services;

import com.construction.feature.project.domain.Project;
import com.construction.feature.project.repositories.ProjectRepository;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.SFWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;
    @Autowired
    private EntityDataMapper mapper;

    @PreAuthorize("hasAuthority('READ_ALL_PROJECT')")
    public ResponseEntity<Object> search(Project project, Pageable pageable) {
        Page<Project> all = repository.findAll(SFWhere.and(project)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_ALL_PROJECT')")
    public Project create(Project project) {
        return repository.save(project);
    }

    @PreAuthorize("hasAuthority('UPDATE_ALL_PROJECT')")
    public Project update(Long id, Project project) {
        var target = repository.findById(id).orElseThrow();
        target = mapper.mapObject(project, target, Project.class);
        return repository.save(target);
    }

    @PreAuthorize("hasAuthority('READ_ALL_PROJECT')")
    public Page<Project> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('READ_ALL_PROJECT')")
    public Project getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PreAuthorize("hasAuthority('DELETE_ALL_PROJECT')")
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
