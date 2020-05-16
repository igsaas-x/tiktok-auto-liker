package com.construction.feature.project.controllers;

import com.construction.feature.project.domain.Project;
import com.construction.feature.project.services.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping("/search")
    ResponseEntity<Object> search(Project project, Pageable pageable) {
        return service.search(project, pageable);
    }

    @GetMapping
    Page<Project> getAllProject(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    Project getProjectById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    Project create(@RequestBody Project project) {
        return service.create(project);
    }

    @PutMapping("/{id}")
    Project update(@PathVariable Long id, @RequestBody Project project) {
        return service.update(id, project);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
