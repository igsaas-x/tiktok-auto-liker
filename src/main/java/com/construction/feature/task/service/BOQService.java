package com.construction.feature.task.service;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.repository.BOQRepository;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.SFWhere;
import com.construction.user.authentication.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BOQService {

    private final BOQRepository repository;
    private final EntityDataMapper dataMapper;
    private final TaskService taskService;
    private final AppUserService userService;

    public BOQ save(BOQ boq) {
        return repository.save(boq);
    }

    public void deleteById(Long id) {
        var boq = getById(id);
        repository.delete(boq);
    }

    public BOQ getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(BOQ.class, id));
    }

    public ResponseEntity<Object> search(BOQ boq, Pageable pageable) {
        Page<BOQ> all = repository.findAll(SFWhere.and(boq)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    public List<BOQ> findAll() {
        return repository.findAll();
    }

    public Page<BOQ> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public BOQ updateById(Long id, BOQ boq) {
        var target = getById(id);
        target = dataMapper.mapObject(boq, target, BOQ.class);
        return repository.save(target);
    }

    public BOQ addTask(Long id, List<Task> tasks) {
        var boq = getById(id);
        boq.getTasks().addAll(tasks);
        return repository.save(boq);
    }

    public BOQ removeTask(Long id, Long taskId) {
        var boq = getById(id);
        var task = taskService.getById(taskId);
        boq.getTasks().remove(task);
        return repository.save(boq);
    }

    public boolean verifyAllTask(Long id) {
        var boq = getById(id);
        taskService.getByBoq(boq).forEach(taskService::verify);
        return true;
    }

    public boolean approveAllTask(Long id) {
        var boq = getById(id);
        taskService.getByBoq(boq).forEach(taskService::approve);
        return true;
    }

    public boolean assign(Long boqId, Long userId, AssignFor assignFor) {
        var user = userService.getById(userId);
        var boq = getById(boqId);
        taskService.getByBoq(boq).forEach(task -> taskService.assign(task, user, assignFor));
        return true;
    }

    public boolean unAssign(Long id, Long userId) {
        var boq = getById(id);
        var user = userService.getById(userId);
        taskService.getByBoq(boq).forEach(task -> taskService.unAssign(task, user));
        return true;
    }
}