package com.construction.organization.workflow.controller;

import com.construction.organization.workflow.domain.WorkFlowNode;
import com.construction.organization.workflow.dto.WorkFlowNodeDto;
import com.construction.organization.workflow.service.WorkFlowNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow/node")
public class WorkFlowNodeController {

    @Autowired
    private WorkFlowNodeService service;

    @GetMapping("/{id}")
    public WorkFlowNode getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public WorkFlowNode create(@RequestBody WorkFlowNodeDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public WorkFlowNode update(@PathVariable Long id, @RequestBody WorkFlowNodeDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        service.delete(id);
        return true;
    }
}
