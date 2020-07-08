package com.construction.feature.project.controllers;

import com.construction.feature.FilterType;
import com.construction.feature.project.domain.Project;
import com.construction.feature.project.domain.ProjectAssign;
import com.construction.feature.project.services.ProjectAssignService;
import com.construction.feature.project.services.ProjectService;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.dto.AssignedDto;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authorization.domain.ActionName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;
    private final FilterConfig filterConfig;
    private final ProjectAssignService assignService;

    @GetMapping("/search")
    public ResponseEntity<Object> search(Project project, Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "project");
        return service.search(project, pageable);
    }

    @GetMapping("/page")
    public Page<Project> getAllProject(Pageable pageable, @RequestParam FilterType filter) {
        filterConfig.configureFilter(ActionName.READ, "project");
        switch (filter) {
            case PENDING_FOR_VERIFY:
                return service.getPendingForVerify(pageable);
            case PENDING_FOR_APPROVE:
                return service.getPendingForApprove(pageable);
            case OWNED:
                return service.getMyObject(pageable);
            case ASSIGNED:
                return service.getAssigned(pageable);
            default:
                return service.getAll(pageable);
        }
    }

    @GetMapping
    public List<Project> getAll() {
        filterConfig.configureFilter(ActionName.READ, "project");
        return service.getAll();
    }

    @GetMapping("/assigned")
    public Page<Project> getAssignedProject(Pageable pageable) {
        return service.getAssigned(pageable);
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

    @DeleteMapping("/batch")
    void deleteAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.DELETE, "project");
        ids.getIds().forEach(service::delete);
    }

    @GetMapping("/pending/verify")
    public Page<Project> getPendingVerify(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "project");
        return service.getPendingForVerify(pageable);
    }

    @GetMapping("/pending/approve")
    public Page<Project> getPendingApprove(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "project");
        return service.getPendingForApprove(pageable);
    }

    @GetMapping("/pending/all")
    public Page<Project> getAllPending(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "project");
        return service.getAllPending(pageable);
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
    public ProjectAssign assignProject(@PathVariable Long id, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "project");
        return service.assign(id, userId, assignFor);
    }

    @PostMapping("/batch/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_PROJECT') or hasAuthority('ASSIGN_ASSIGNED_PROJECT')")
    public boolean assignAllProject(@RequestBody IdList ids, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "project");
        ids.getIds().forEach(id -> service.assign(id, userId, assignFor));
        return true;
    }

    @PostMapping("/{id}/unassign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_PROJECT') or hasAuthority('ASSIGN_ASSIGNED_PROJECT')")
    public boolean unAssignProject(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.ASSIGN, "project");
        return service.unAssign(id, userId);
    }

    @GetMapping("/{id}/assigned/user")
    public List<AssignedDto> getAssignedUser(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.READ, "project");
        return assignService.getAssignedUser(id);
    }
}
