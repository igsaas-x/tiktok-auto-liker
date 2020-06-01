package com.construction.feature.task.controller;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.domain.TaskAssign;
import com.construction.feature.task.dto.TaskDto;
import com.construction.feature.task.service.TaskService;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/task")
@RestController
@Api(tags = "Task API")
public class TaskController {

    @Autowired
    private TaskService service;
    @Autowired
    private FilterConfig filterConfig;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_TASK')")
    public Task save(@RequestBody TaskDto task) {
        return service.save(task);
    }

    @GetMapping("/{id}")
    public Task findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.getById(id);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "task");
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<Task> list() {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<Task> pageQuery(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.findAll(pageable);
    }

    @GetMapping("/pending/verify")
    public List<Task> getPendingForVerifyTask() {
        return service.getPendingForVerify();
    }

    @GetMapping("/pending/approve")
    public List<Task> getPendingForApproveTask() {
        return service.getPendingForApprove();
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody TaskDto dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "task");
        return service.updateById(id, dto);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_TASK') or hasAuthority('VERIFY_ASSIGNED_TASK')")
    public Task verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "task");
        return service.verify(id);
    }

    @PostMapping("/batch/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_TASK') or hasAuthority('VERIFY_ASSIGNED_TASK')")
    public List<Task> verifyAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.VERIFY, "task");
        return service.verifyAll(ids.getIds());
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_TASK') or hasAuthority('APPROVE_ASSIGNED_TASK')")
    public Task approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "task");
        return service.approve(id);
    }

    @PostMapping("/batch/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_TASK') or hasAuthority('APPROVE_ASSIGNED_TASK')")
    public List<Task> approveAll(@PathVariable IdList ids) {
        filterConfig.configureFilter(ActionName.APPROVE, "task");
        return service.approveAll(ids.getIds());
    }

    @PostMapping("/{id}/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_TASK') or hasAuthority('ASSIGN_ASSIGNED_TASK')")
    public TaskAssign assign(@PathVariable Long id, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
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