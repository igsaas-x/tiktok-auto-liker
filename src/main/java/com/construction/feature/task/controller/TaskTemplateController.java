package com.construction.feature.task.controller;

import com.construction.feature.task.domain.TaskTemplate;
import com.construction.feature.task.dto.TaskTemplateDto;
import com.construction.feature.task.dto.mapper.TaskTemplateMapper;
import com.construction.feature.task.service.TaskTemplateService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/task-template")
@RestController
@Api(tags = "Task Template API")
@RequiredArgsConstructor
public class TaskTemplateController {

    private final TaskTemplateService service;
    private final TaskTemplateMapper mapper;

    @PostMapping
    public TaskTemplate create(@RequestBody TaskTemplateDto dto) {
        final var template = mapper.toEntity(dto);
        return service.create(template);
    }

    @PutMapping("/{id}")
    public TaskTemplate update(@PathVariable Long id, @RequestBody TaskTemplateDto dto) {
        final var template = mapper.toEntity(dto);
        return service.update(id, template);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        return Map.of("success", service.delete(id));
    }

    @GetMapping
    public Page<TaskTemplate> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public TaskTemplateDto getById(@PathVariable Long id) {
        return mapper.apply(service.getById(id));
    }

    @GetMapping("/code/{code}")
    public TaskTemplate getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }

    @GetMapping("/parent/{id}")
    public List<TaskTemplate> getByParentId(@PathVariable Long id) {
        return service.getByParentId(id);
    }

}
