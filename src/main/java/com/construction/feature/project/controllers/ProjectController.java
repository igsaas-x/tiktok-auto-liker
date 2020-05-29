package com.construction.feature.project.controllers;

import com.construction.feature.project.domain.Project;
import com.construction.feature.project.domain.ProjectAssign;
import com.construction.feature.project.services.ProjectService;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Object> search(Project project, Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "project");
        return service.search(project, pageable);
    }

    @GetMapping
    public Page<Project> getAllProject(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "project");
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.READ, "project");
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_PROJECT')")
    public Project create(@RequestBody Project project) {
        return service.create(project);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable Long id, @RequestBody Project project) {
        filterConfig.configureFilter(ActionName.UPDATE, "project");
        return service.update(id, project);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "project");
        service.delete(id);
    }

    @GetMapping("/pending")
    public List<Project> getPendingProject(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "project");
        return service.getPendingProject(pageable);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_PROJECT') or hasAuthority('VERIFY_ASSIGNED_PROJECT')")
    public Project verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "project");
        return service.verify(id);
    }

    @PostMapping("/batch/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_PROJECT') or hasAuthority('VERIFY_ASSIGNED_PROJECT')")
    public List<Project> verifyAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.VERIFY, "project");
        return service.verifyAll(ids.getIds());
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_PROJECT') or hasAuthority('APPROVE_ASSIGNED_PROJECT')")
    public Project approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "project");
        return service.approve(id);
    }

    @PostMapping("/batch/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_PROJECT') or hasAuthority('APPROVE_ASSIGNED_PROJECT')")
    public List<Project> approveAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.APPROVE, "project");
        return service.approveAll(ids.getIds());
    }

    @PostMapping("/{id}/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_PROJECT') or hasAuthority('ASSIGN_ASSIGNED_PROJECT')")
    public ProjectAssign assignProject(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.ASSIGN, "project");
        return service.assign(id, userId);
    }

    @PostMapping("/{id}/unassign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_PROJECT') or hasAuthority('ASSIGN_ASSIGNED_PROJECT')")
    public boolean unAssignProject(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.ASSIGN, "project");
        return service.unAssign(id, userId);
    }
}
