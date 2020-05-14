package com.construction.feature.task.controllers;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.services.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

    final
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    ResponseEntity<Object> search(Task task, Pageable pageable) {
        return taskService.search(task, pageable);
    }

    @PostMapping
    void create() {
    }

    @PutMapping
    void update() {
    }

    @DeleteMapping("/")
    void delete() {
    }
}
