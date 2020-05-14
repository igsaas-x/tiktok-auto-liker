package com.construction.feature.project.services;

import com.construction.feature.project.domain.Project;
import com.construction.feature.project.repositories.ProjectRepository;
import com.construction.persistence.utils.SFWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    public ResponseEntity<Object> search(Project project, Pageable pageable) {
        Page<Project> all = projectRepository.findAll(SFWhere.and(project)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
