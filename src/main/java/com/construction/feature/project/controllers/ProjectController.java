package com.construction.feature.project.controllers;

import com.construction.feature.project.domain.Project;
import com.construction.feature.project.services.ProjectService;
import com.construction.persistence.filter.FilterConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService service;
    private final FilterConfig filterConfig;

    public ProjectController(ProjectService service, FilterConfig filterConfig) {
        this.service = service;
        this.filterConfig = filterConfig;
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('READ_ALL_PROJECT') or hasAuthority('READ_ASSIGNED_PROJECT')")
    ResponseEntity<Object> search(Project project, Pageable pageable) {
        filterConfig.configureFilter("read", "project");
        return service.search(project, pageable);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_PROJECT') or hasAuthority('READ_ASSIGNED_PROJECT')")
    Page<Project> getAllProject(Pageable pageable) {
        filterConfig.configureFilter("read", "project");
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ALL_PROJECT') or hasAuthority('READ_ASSIGNED_PROJECT')")
    Project getProjectById(@PathVariable Long id) {
        filterConfig.configureFilter("read", "project");
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_PROJECT')")
    Project create(@RequestBody Project project) {
        return service.create(project);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ALL_PROJECT') or hasAuthority('UPDATE_ASSIGNED_PROJECT')")
    Object update(@PathVariable Long id, @RequestBody Project project) {
        filterConfig.configureFilter("update", "project");
        return service.update(id, project);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ALL_PROJECT') or hasAuthority('DELETE_ASSIGNED_PROJECT')")
    void delete(@PathVariable Long id) {
        filterConfig.configureFilter("delete", "project");
        service.delete(id);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_PROJECT') or hasAuthority('VERIFY_ASSIGNED_PROJECT')")
    Project verify(@PathVariable Long id) {
        filterConfig.configureFilter("verify", "project");
        return service.verify(id);
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_PROJECT') or hasAuthority('APPROVE_ASSIGNED_PROJECT')")
    Project approve(@PathVariable Long id) {
        filterConfig.configureFilter("approve", "project");
        return service.approve(id);
    }
}
