package com.construction.feature.task.controller;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.dto.BOQDto;
import com.construction.feature.task.dto.TaskDto;
import com.construction.feature.task.dto.mapper.BOQMapper;
import com.construction.feature.task.dto.mapper.TaskMapper;
import com.construction.feature.task.service.BOQService;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/boq")
@RestController
@Api(tags = "BOQ API")
@AllArgsConstructor
public class BOQController {

    private final BOQService service;
    private final FilterConfig filterConfig;
    private final TaskMapper taskMapper;
    private final BOQMapper mapper;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_BOQ')")
    public BOQDto create(@RequestBody BOQDto dto) {
        return mapper.apply(service.save(mapper.toEntity(dto)));
    }

    @GetMapping("/{id}")
    public BOQDto findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "boq");
        return mapper.apply(service.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(BOQ boq, Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "boq");
        return service.search(boq, pageable);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "boq");
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<BOQDto> list() {
        filterConfig.configureFilter(ActionName.READ, "boq");
        return service.findAll().stream().map(mapper).collect(Collectors.toList());
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<BOQDto> pageQuery(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "boq");
        return service.findAll(pageable).map(mapper);
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    public BOQDto update(@PathVariable Long id, @RequestBody BOQDto dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "boq");
        return mapper.apply(service.updateById(id, mapper.toEntity(dto)));
    }

    @PutMapping("/{id}/add-task")
    public BOQDto addTask(@PathVariable Long id, @RequestBody List<TaskDto> dtos) {
        filterConfig.configureFilter(ActionName.UPDATE, "boq");
        final var tasks = dtos.stream().map(taskMapper::toEntity).collect(Collectors.toList());
        return mapper.apply(service.addTask(id, tasks));
    }

    @PutMapping("/{id}/remove-task/{taskId}")
    public BOQDto removeTask(@PathVariable Long id, @PathVariable Long taskId) {
        filterConfig.configureFilter(ActionName.UPDATE, "boq");
        return mapper.apply(service.removeTask(id, taskId));
    }

    @ApiOperation("verify BOQ mean to verify all task in BOQ")
    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_TASK') or hasAuthority('VERIFY_ASSIGNED_TASK')")
    public boolean verifyAllTaskInBoq(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "task");
        return service.verifyAllTask(id);
    }

    @ApiOperation("approve BOQ mean to approve all task in BOQ")
    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_TASK') or hasAuthority('APPROVE_ASSIGNED_TASK')")
    public boolean approveAllTaskInBoq(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "task");
        return service.approveAllTask(id);
    }

    @PostMapping("/{id}/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_TASK') or hasAuthority('ASSIGN_ASSIGNED_TASK')")
    public boolean assign(@PathVariable Long id, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        return service.assign(id, userId, assignFor);
    }

    @PostMapping("/{id}/unassign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_TASK') or hasAuthority('ASSIGN_ASSIGNED_TASK')")
    public boolean unAssign(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        return service.unAssign(id, userId);
    }
}