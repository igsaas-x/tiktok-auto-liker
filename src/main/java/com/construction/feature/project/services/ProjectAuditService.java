package com.construction.feature.project.services;

import com.construction.feature.project.domain.ProjectAudit;
import com.construction.feature.project.repositories.ProjectAuditRepository;
import com.construction.persistence.utils.SFWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class ProjectAuditService {

    @Autowired
    private ProjectAuditRepository projectAuditRepository;

    public ResponseEntity<Object> search(ProjectAudit projectAudit, Pageable pageable) {
        Page<ProjectAudit> all = projectAuditRepository.findAll(SFWhere.and(projectAudit)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
