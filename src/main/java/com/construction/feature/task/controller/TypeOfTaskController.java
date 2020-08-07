package com.construction.feature.task.controller;

import com.construction.feature.task.domain.TypeOfTask;
import com.construction.feature.task.repository.TypeOfTaskRepository;
import com.construction.persistence.service.EntityDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type-of-task")
@RequiredArgsConstructor
public class TypeOfTaskController {

    private final TypeOfTaskRepository repository;
    private final EntityDataMapper dataMapper;

    @GetMapping
    public List<TypeOfTask> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public TypeOfTask getOneById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ALL_ALL_ALL')")
    public TypeOfTask create(@RequestBody TypeOfTask typeOfTask) {
        return repository.save(typeOfTask);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ALL_ALL_ALL')")
    public TypeOfTask update(@PathVariable Long id, @RequestBody TypeOfTask typeOfTask) {
        var target = repository.findById(id).orElseThrow();
        target = dataMapper.mapObject(typeOfTask, target, TypeOfTask.class);
        return repository.save(target);
    }

    @DeleteMapping("/id")
    @PreAuthorize("hasAuthority('ALL_ALL_ALL')")
    public void deleteById(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
