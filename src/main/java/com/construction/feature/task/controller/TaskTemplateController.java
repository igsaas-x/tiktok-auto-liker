package com.construction.feature.task.controller;

import com.construction.feature.task.domain.TaskTemplate;
import com.construction.feature.task.dto.TaskTemplateData;
import com.construction.feature.task.dto.TaskTemplateDto;
import com.construction.feature.task.dto.mapper.TaskTemplateMapper;
import com.construction.feature.task.repository.TaskDataRepository;
import com.construction.feature.task.service.TaskTemplateService;
import com.construction.persistence.dto.IdList;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/task-template")
@RestController
@Api(tags = "Task Template API")
@RequiredArgsConstructor
public class TaskTemplateController {

    private final TaskTemplateService service;
    private final TaskTemplateMapper mapper;
    private final TaskDataRepository dataRepository;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_TASK_TEMPLATE')")
    public TaskTemplate create(@RequestBody TaskTemplateDto dto) {
        final var template = mapper.toEntity(dto);
        return service.create(template);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_TASK_TEMPLATE')")
    public TaskTemplate update(@PathVariable Long id, @RequestBody TaskTemplateDto dto) {
        final var template = mapper.toEntity(dto);
        return service.update(id, template);
    }

    @PutMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_TASK_TEMPLATE')")
    public TaskTemplate verify(@PathVariable Long id) {
        return service.verify(id);
    }

    @PutMapping("/verify/all")
    @PreAuthorize("hasAuthority('VERIFY_TASK_TEMPLATE')")
    public void verifyAll(@RequestBody IdList ids) {
        ids.getIds().forEach(id -> {
            try {
                service.verify(id);
            } catch (final Exception e) {
                log.warn("verify fail:", e);
            }
        });
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_TASK_TEMPLATE')")
    public TaskTemplate approve(@PathVariable Long id) {
        return service.approve(id);
    }

    @PutMapping("/approve/all")
    @PreAuthorize("hasAuthority('APPROVE_TASK_TEMPLATE')")
    public void approveAll(@RequestBody IdList ids) {
        ids.getIds().forEach(id -> {
            try {
                service.approve(id);
            } catch (final Exception e) {
                log.warn("approve fail:", e);
            }
        });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TASK_TEMPLATE')")
    public Map<String, Object> delete(@PathVariable Long id) {
        return Map.of("success", service.delete(id));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DELETE_TASK_TEMPLATE')")
    public void deleteAll(@RequestBody IdList ids) {
        ids.getIds().forEach(id -> {
            try {
                service.delete(id);
            } catch (final Exception e) {
                log.warn("delete fail:", e);
            }
        });
    }

    @GetMapping
    public Page<TaskTemplate> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/all-data")
    public List<TaskTemplateData> getAllData() {
        return dataRepository.findAllByFirstLevel(true);
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
