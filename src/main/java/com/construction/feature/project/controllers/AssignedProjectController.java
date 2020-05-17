package com.construction.feature.project.controllers;

import com.construction.feature.project.domain.Project;
import com.construction.feature.project.domain.ProjectAssign;
import com.construction.feature.project.repositories.ProjectAssignRepository;
import com.construction.feature.project.repositories.ProjectRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authentication.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class AssignedProjectController {

    @Autowired
    private ProjectAssignRepository repository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private FilterConfig filterConfig;

    @PostMapping("/{id}/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_PROJECT') or hasAuthority('ASSIGN_ASSIGNED_PROJECT')")
    public ProjectAssign assignProject(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter("project");
        var project = projectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Project.class, id)
        );
        var user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(AppUser.class, userId)
        );
        var projectAssign = new ProjectAssign().setProject(project).setAppUser(user);
        return repository.save(projectAssign);
    }
}
