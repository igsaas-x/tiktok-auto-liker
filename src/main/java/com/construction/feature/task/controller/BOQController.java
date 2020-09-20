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
    @PreAuthorize("hasAuthority('CREATE_BOQ')")
    public boolean create(@RequestBody BOQDto dto) {
        service.save(mapper.toEntity(dto));
        return true;
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
}