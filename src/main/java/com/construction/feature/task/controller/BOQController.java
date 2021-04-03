package com.construction.feature.task.controller;

import com.construction.feature.task.dto.BOQData;
import com.construction.feature.task.dto.BOQDto;
import com.construction.feature.task.dto.TaskDto;
import com.construction.feature.task.dto.mapper.BOQDataMapper;
import com.construction.feature.task.dto.mapper.BOQMapper;
import com.construction.feature.task.dto.mapper.TaskMapper;
import com.construction.feature.task.repository.BOQRepository;
import com.construction.feature.task.service.BOQService;
import com.construction.persistence.dto.SidList;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/boq")
@RestController
@Api(tags = "BOQ API")
@RequiredArgsConstructor
public class BOQController {

    private final BOQRepository repository;
    private final BOQService service;
    private final FilterConfig filterConfig;
    private final TaskMapper taskMapper;
    private final BOQMapper mapper;
    private final BOQDataMapper dataMapper;

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
    public List<BOQData> search(String code,
                                Long projectId,
                                Long houseId,
                                Long streetId,
                                Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "boq");
        return service.search(code, projectId, houseId, streetId, pageable)
                .stream()
                .map(dataMapper)
                .collect(Collectors.toList());
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "boq");
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<BOQData> list() {
        filterConfig.configureFilter(ActionName.READ, "boq");
        return service.findAll().stream().map(dataMapper).collect(Collectors.toList());
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<BOQData> pageQuery(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "boq");
        return service.findAll(pageable).map(dataMapper);
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

    @GetMapping("/code/{code}")
    public Map<String, Object> codeExist(@PathVariable("code") String code) {
        return Map.of("exist", repository.findByCode(code).isPresent());
    }

    @PostMapping("{id}/assign/sub-constructor")
    public Map<String, Object> assignBOQ(@PathVariable Long id, @RequestBody @Valid final SidList sids) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        service.assign(id, sids);
        return Map.of("success", true);
    }

    @PostMapping("{id}/unassign/sub-constructor")
    public Map<String, Object> unAssign(@PathVariable Long id, @RequestBody @Valid final SidList sids) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        service.unAssign(id, sids);
        return Map.of("success", true);
    }
}