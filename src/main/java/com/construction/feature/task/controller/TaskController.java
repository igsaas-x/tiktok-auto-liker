package com.construction.feature.task.controller;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/task")
@RestController
@Api(tags = "Task API")
public class TaskController {

    @Autowired
    private TaskService service;

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody Task task) {
        service.save(task);
    }

    @GetMapping("/{id}")
    public Task findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<Task> list() {
        return service.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<Task> pageQuery(Pageable pageable) {
        return service.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task dto) {
        return service.updateById(id, dto);
    }
}