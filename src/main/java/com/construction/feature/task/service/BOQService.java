package com.construction.feature.task.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.dto.BOQDto;
import com.construction.feature.task.dto.BOQMapper;
import com.construction.feature.task.repository.BOQRepository;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.ObjectStatusValidator;
import com.construction.user.authentication.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BOQService {

    @Autowired
    private BOQRepository repository;
    @Autowired
    private EntityDataMapper dataMapper;
    @Autowired
    private ObjectStatusValidator<Task> validator;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AppUserService userService;
    @Autowired
    private ApplicationSecurityContext context;
    @Autowired
    private BOQMapper mapper;

    public BOQ save(BOQDto dto) {
        var boq = mapper.toEntity(dto);
        return repository.save(boq);
    }

    public void deleteById(Long id) {
        var boq = getById(id);
        repository.delete(boq);
    }

    public BOQ getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(BOQ.class, id));
    }

    public List<BOQ> findAll() {
        return repository.findAll();
    }

    public Page<BOQ> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public BOQ updateById(Long id, BOQDto dto) {
        var boq = mapper.toEntity(dto);
        var target = getById(id);
        target = dataMapper.mapObject(boq, target, BOQ.class);
        return repository.save(target);
    }

    public boolean verifyAllTask(Long id) {
        var boq = getById(id);
        taskService.getByBoq(boq).forEach(task -> taskService.verify(task));
        return true;
    }

    public boolean approveAllTask(Long id) {
        var boq = getById(id);
        taskService.getByBoq(boq).forEach(task -> taskService.approve(task));
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