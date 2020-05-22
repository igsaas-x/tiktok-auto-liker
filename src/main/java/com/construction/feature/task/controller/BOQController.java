package com.construction.feature.task.controller;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.BOQAssign;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.service.BOQService;
import com.construction.feature.task.service.TaskService;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authentication.service.AppUserService;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/boq")
@RestController
@Api(tags = "BOQ API")
public class BOQController {

    @Autowired
    private BOQService service;
    @Autowired
    private FilterConfig filterConfig;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AppUserService userService;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_BOQ')")
    public void save(@RequestBody BOQ bOQ) {
        service.save(bOQ);
    }

    @GetMapping("/{id}")
    public BOQ findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "boq");
        return service.getById(id);
    }

    @GetMapping("/{id}/tasks")
    public List<Task> findTaskByBoqId(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "boq");
        var boq = service.getById(id);
        return taskService.findByBoq(boq);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "boq");
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<BOQ> list() {
        filterConfig.configureFilter(ActionName.READ, "boq");
        return service.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<BOQ> pageQuery(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "boq");
        return service.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    public BOQ update(@PathVariable Long id, @RequestBody BOQ dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "boq");
        return service.updateById(id, dto);
    }

    @PostMapping("/{id}/tasks/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_TASK') or hasAuthority('VERIFY_ASSIGNED_TASK')")
    public boolean verifyAllTaskInBoq(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "task");
        return service.verifyAllTask(id);
    }

    @PostMapping("/{id}/tasks/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_TASK') or hasAuthority('APPROVE_ASSIGNED_TASK')")
    public boolean approveAllTaskInBoq(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "task");
        return service.approveAllTask(id);
    }

    @PostMapping("/{id}/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_BOQ') or hasAuthority('ASSIGN_ASSIGNED_BOQ')")
    public BOQAssign assign(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.ASSIGN, "boq");
        return service.assign(id, userId);
    }

    @PostMapping("/{id}/unassign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_BOQ') or hasAuthority('ASSIGN_ASSIGNED_BOQ')")
    public boolean unAssign(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.ASSIGN, "boq");
        return service.unAssign(id, userId);
    }
}