package com.construction.feature.task.controller;

import com.construction.feature.FilterType;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.domain.TaskAssign;
import com.construction.feature.task.dto.TaskDto;
import com.construction.feature.task.dto.mapper.TaskMapper;
import com.construction.feature.task.service.TaskAssignService;
import com.construction.feature.task.service.TaskService;
import com.construction.feature.task.service.TaskSubConstructAssignService;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.dto.AssignedDto;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.dto.IdListBatch;
import com.construction.persistence.dto.IdListUserListBatch;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/task")
@RestController
@Api(tags = "Task API")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;
    private final FilterConfig filterConfig;
    private final TaskMapper mapper;
    private final TaskAssignService assignService;
    private final TaskSubConstructAssignService subConstructAssignService;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_TASK')")
    public TaskDto save(@RequestBody TaskDto task) {
        return mapper.apply(service.save(mapper.toEntity(task)));
    }

    @GetMapping("/{id}")
    public TaskDto findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return mapper.apply(service.getById(id));
    }

    @ApiOperation("Delete by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "task");
        service.deleteById(id);
    }

    @ApiOperation("Delete batch by Id")
    @DeleteMapping
    public void deleteAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.DELETE, "task");
        ids.getIds().forEach(service::deleteById);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(LocalDate requestDate,
                                         Long subConstructorId,
                                         Long projectId,
                                         Long streetId,
                                         Long houseId,
                                         Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return null;
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<TaskDto> list() {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.getAll().stream().map(mapper).collect(Collectors.toList());
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<TaskDto> pageQuery(Pageable pageable, @RequestParam FilterType filter) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.getAll(pageable, filter).map(mapper);
    }

    @GetMapping("/pending/page")
    public Page<TaskDto> getAllPendingTask(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.getAllPending(pageable).map(mapper);
    }

    @GetMapping("/pending")
    public List<TaskDto> getPendingForAll() {
        filterConfig.configureFilter(ActionName.READ, "task");
        final var all = service.getPendingForVerify().stream().map(mapper).collect(Collectors.toList());
        all.addAll(service.getPendingForApprove().stream().map(mapper).collect(Collectors.toList()));
        return all;
    }

    @GetMapping("/assigned")
    public List<TaskDto> getAssignedTask() {
        filterConfig.configureFilter(ActionName.READ, "task");
        return service.getAssignedTask().stream().map(mapper).collect(Collectors.toList());
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody TaskDto dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "task");
        return service.updateById(id, mapper.toEntity(dto));
    }

    @PostMapping("/{id}/verify")
    public TaskDto verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "task");
        return mapper.apply(service.verify(id));
    }

    @PostMapping("/batch/verify")
    public List<TaskDto> verifyAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.VERIFY, "task");
        return service.verifyAll(ids.getIds()).stream().map(mapper).collect(Collectors.toList());
    }

    @PostMapping("/{id}/approve")
    public TaskDto approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "task");
        return mapper.apply(service.approve(id));
    }

    @PostMapping("/batch/approve")
    public List<TaskDto> approveAll(@PathVariable IdList ids) {
        filterConfig.configureFilter(ActionName.APPROVE, "task");
        return service.approveAll(ids.getIds()).stream().map(mapper).collect(Collectors.toList());
    }

    @PostMapping("/{id}/assign/{userId}")
    public TaskAssign assign(@PathVariable Long id, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        return service.assign(id, userId, assignFor);
    }

    @PostMapping("/batch/assign/{userId}")
    public boolean assignAll(@RequestBody IdList ids, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        ids.getIds().forEach(id -> service.assign(id, userId, assignFor));
        return true;
    }

    @PostMapping("/batch/assign")
    public boolean assignAllBatch(@RequestBody IdListUserListBatch ids, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        ids.getIds().forEach(id -> {
            ids.getUserIds().forEach(userId -> {
                try {
                    service.assign(id, userId, assignFor);
                } catch (final Exception e) {
                    log.error(String.format("error assign task id %s to user id %s", id, userId));
                }
            });
        });
        return true;
    }

    @PostMapping("/{id}/unassign/{userId}")
    public boolean unAssign(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        return service.unAssign(id, userId);
    }

    @GetMapping("/{id}/assigned/user")
    public List<AssignedDto> getAssignedUser(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.READ, "task");
        return assignService.getAssignedUser(id);
    }

    @PostMapping("/{id}/assign/sub-constructor/{sid}")
    public Map<String, Boolean> assignToSubConstructor(@PathVariable Long id, @PathVariable Long sid) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        subConstructAssignService.assign(id, List.of(sid));
        return Map.of("success", true);
    }

    @PostMapping("/batch/assign/sub-constructor/batch")
    public Map<String, Boolean> batchAssignToSubConstructor(@RequestBody final IdListBatch batch) {
        filterConfig.configureFilter(ActionName.ASSIGN, "task");
        batch.getIds().forEach(taskId -> {
            try {
                subConstructAssignService.assign(taskId, batch.getSids());
            } catch (Exception e) {
                log.error("error assign task");
            }
        });
        return Map.of("success", true);
    }

    @GetMapping("/by-sub-constructor/{id}")
    public List<TaskDto> getBySubConstructorId(@PathVariable Long id) {
        return service.getBySubConstructorId(id).stream().map(mapper).collect(Collectors.toList());
    }
}