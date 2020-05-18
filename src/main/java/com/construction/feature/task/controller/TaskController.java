package com.construction.feature.task.controller;

import com.construction.feature.task.dto.TaskDTO;
import com.construction.feature.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/task")
@RestController
@Api(tags = "Task API")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody TaskDTO task) {
        taskService.save(task);
    }

    @ApiOperation("Delete based on primary key")
    @GetMapping("/{id}")
    public TaskDTO findById(@PathVariable("id") Long id) {
        Optional<TaskDTO> dtoOptional = taskService.findById(id);
        return dtoOptional.orElse(null);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        taskService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<TaskDTO> list() {
        return taskService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<TaskDTO> pageQuery(Pageable pageable) {
        return taskService.findAll(pageable);
    }

/*    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public TaskDTO update(@RequestBody TaskDTO dto) {
        return taskService.updateById(dto);
    }*/
}