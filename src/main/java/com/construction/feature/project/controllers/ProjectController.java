package com.construction.feature.project.controllers;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
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
    private final ApplicationSecurityContext context;
    private final FilterConfig filterConfig;

    public ProjectController(ProjectService service, ApplicationSecurityContext context, FilterConfig filterConfig) {
        this.service = service;
        this.context = context;
        this.filterConfig = filterConfig;
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('READ_ALL_PROJECT')")
    ResponseEntity<Object> search(Project project, Pageable pageable) {
        return service.search(project, pageable);
    }

    @GetMapping
    Page<Project> getAllProject(Pageable pageable) {
        filterConfig.configureFilter("project");
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ALL_PROJECT')")
    Project getProjectById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_PROJECT')")
    Project create(@RequestBody Project project) {
        return service.create(project);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ALL_PROJECT')")
    Project update(@PathVariable Long id, @RequestBody Project project) {
        return service.update(id, project);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ALL_PROJECT')")
    void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
