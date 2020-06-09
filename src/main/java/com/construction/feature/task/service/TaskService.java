package com.construction.feature.task.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.domain.TaskAssign;
import com.construction.feature.task.dto.TaskMapper;
import com.construction.feature.task.repository.TaskAssignRepository;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.domain.AssignStatus;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.ObjectStatusValidator;
import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authentication.service.AppUserService;
import com.construction.user.authorization.domain.ActionName;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final EntityDataMapper dataMapper;
    private final ObjectStatusValidator<Task> validator;
    private final ApplicationSecurityContext context;
    private final AppUserService userService;
    private final TaskAssignRepository assignRepository;

    public Task save(Task task) {
        return repository.save(task);
    }

    public void save(List<Task> tasks) {
        repository.saveAll(tasks);
    }

    public void deleteById(Long id) {
        var task = getById(id);
        repository.delete(task);
    }

    public Task getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Task.class, id));
    }

    public List<Task> getByBoq(BOQ boq) {
        return repository.findAllByBoq(boq);
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    public Page<Task> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Task> getPendingForVerify() {
        return repository.getPendingTask(context.authenticatedUser().getId(), AssignFor.VERIFY.name(), ObjectStatus.OPEN.name());
    }

    public List<Task> getPendingForApprove() {
        return repository.getPendingTask(context.authenticatedUser().getId(), AssignFor.APPROVE.name(), ObjectStatus.VERIFIED.name());
    }

    public Task updateById(Long id, Task task) {
        var target = getById(id);
        validator.validateStatus(target, ActionName.UPDATE);
        target = dataMapper.mapObject(task, target, Task.class);
        return repository.save(target);
    }

    public Task verify(Long id) {
        var task = getById(id);
        return verify(task);
    }

    public List<Task> verifyAll(List<Long> ids) {
        return ids.stream().map(this::verify).collect(Collectors.toList());
    }

    public Task verify(Task task) {
        validator.validateStatus(task, ActionName.VERIFY);
        task.setStatus(ObjectStatus.VERIFIED);
        task.setVerifiedAt(LocalDateTime.now());
        task.setVerifiedBy(context.authenticatedUser());
        return repository.save(task);
    }

    public Task approve(Long id) {
        var task = getById(id);
        return approve(task);
    }

    public List<Task> approveAll(List<Long> ids) {
        return ids.stream().map(this::approve).collect(Collectors.toList());
    }

    public Task approve(Task task) {
        validator.validateStatus(task, ActionName.APPROVE);
        task.setStatus(ObjectStatus.APPROVED);
        task.setApprovedAt(LocalDateTime.now());
        task.setApprovedBy(context.authenticatedUser());
        return repository.save(task);
    }

    public TaskAssign assign(Long id, Long userId, AssignFor assignFor) {
        var task = getById(id);
        var user = userService.getById(userId);
        return assign(task, user, assignFor);
    }

    public TaskAssign assign(Task task, AppUser user, AssignFor assignFor) {
        var taskAssign = new TaskAssign();
        taskAssign.setTask(task);
        taskAssign.setAppUser(user);
        taskAssign.setStatus(AssignStatus.ACTIVE);
        taskAssign.setAssignFor(assignFor);
        return assignRepository.save(taskAssign);
    }

    public boolean unAssign(Long id, Long userId) {
        var task = getById(id);
        var user = userService.getById(userId);
        return unAssign(task, user);
    }

    public boolean unAssign(Task task, AppUser user) {
        var taskAssign = assignRepository.findByTaskAndAppUser(task, user).orElseThrow();
        taskAssign.setStatus(AssignStatus.DELETED);
        return true;
    }
}