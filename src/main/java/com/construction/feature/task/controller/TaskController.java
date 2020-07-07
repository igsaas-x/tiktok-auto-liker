package com.construction.feature.task.controller;

import com.construction.feature.FilterType;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.domain.TaskAssign;
import com.construction.feature.task.dto.TaskDto;
import com.construction.feature.task.dto.TaskMapper;
import com.construction.feature.task.service.TaskService;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.dto.IdList;
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

@RequestMapping("/task")
@RestController
@Api(tags = "Task API")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;
    private final FilterConfig filterConfig;
    private final TaskMapper mapper;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_TASK')")
    public TaskDto save(@RequestBody TaskDto task) {
        return mapper.toDto(service.save(mapper.toEntity(task)));
    }

    @GetMapping("/{id}")
    public TaskDto findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return mapper.toDto(service.getById(id));
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "task");
        service.deleteById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(Task task, Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.search(task, pageable);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<TaskDto> list() {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<Task> pageQuery(Pageable pageable, @RequestParam FilterType filter) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.getAll(pageable, filter);
    }

    @GetMapping("/page/pending/all")
    public Page<Task> getAllPendingTask(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.getAllPending(pageable);
    }

    @GetMapping("/pending/verify")
    public List<TaskDto> getPendingForVerifyTask() {
        return service.getPendingForVerify().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/pending/approve")
    public List<TaskDto> getPendingForApproveTask() {
        return service.getPendingForApprove().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/pending/all")
    public List<TaskDto> getPendingForAll() {
        final var all = getPendingForVerifyTask();
        all.addAll(getPendingForApproveTask());
        return all;
    }

    @GetMapping("/assigned")
    public List<TaskDto> getAssignedTask() {
        return service.getAssignedTask().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    public TaskDto update(@PathVariable Long id, @RequestBody TaskDto dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "task");
        return mapper.toDto(service.updateById(id, mapper.toEntity(dto)));
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_TASK') or hasAuthority('VERIFY_ASSIGNED_TASK')")
    public TaskDto verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "task");
        return mapper.toDto(service.verify(id));
    }

    @PostMapping("/batch/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_TASK') or hasAuthority('VERIFY_ASSIGNED_TASK')")
    public List<TaskDto> verifyAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.VERIFY, "task");
        return service.verifyAll(ids.getIds()).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_TASK') or hasAuthority('APPROVE_ASSIGNED_TASK')")
    public TaskDto approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "task");
        return mapper.toDto(service.approve(id));
    }

    @PostMapping("/batch/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_TASK') or hasAuthority('APPROVE_ASSIGNED_TASK')")
    public List<TaskDto> approveAll(@PathVariable IdList ids) {
        filterConfig.configureFilter(ActionName.APPROVE, "task");
        return service.approveAll(ids.getIds()).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/{id}/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_TASK') or hasAuthority('ASSIGN_ASSIGNED_TASK')")
    public TaskAssign assign(@PathVariable Long id, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        return service.assign(id, userId, assignFor);
    }

    @PostMapping("/batch/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_TASK') or hasAuthority('ASSIGN_ASSIGNED_TASK')")
    public boolean assignAll(@RequestBody IdList ids, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        ids.getIds().forEach(id -> service.assign(id, userId, assignFor));
        return true;
    }

    @PostMapping("/{id}/unassign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_TASK') or hasAuthority('ASSIGN_ASSIGNED_TASK')")
    public boolean unAssign(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        return service.unAssign(id, userId);
    }
}