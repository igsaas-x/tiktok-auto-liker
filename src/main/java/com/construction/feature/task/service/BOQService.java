package com.construction.feature.task.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.BOQAssign;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.repository.BOQAssignRepository;
import com.construction.feature.task.repository.BOQRepository;
import com.construction.persistence.domain.AssignStatus;
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
    private BOQAssignRepository assignRepository;

    public BOQ save(BOQ dto) {
        return repository.save(dto);
    }

    public void save(List<BOQ> dtos) {
        repository.saveAll(dtos);
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

    public BOQ updateById(Long id, BOQ boq) {
        var target = getById(id);
        target = dataMapper.mapObject(boq, target, BOQ.class);
        return repository.save(target);
    }

    public boolean verifyAllTask(Long id) {
        var boq = getById(id);
        taskService.findByBoq(boq).forEach(task -> taskService.verify(task));
        return true;
    }

    public boolean approveAllTask(Long id) {
        var boq = getById(id);
        taskService.findByBoq(boq).forEach(task -> taskService.approve(task));
        return true;
    }

    public BOQAssign assign(Long boqId, Long userId) {
        var user = userService.getById(userId);
        var boq = getById(boqId);
        var boqAssign = new BOQAssign();
        boqAssign.setAppUser(user);
        boqAssign.setBoq(boq);
        boqAssign.setStatus(AssignStatus.ACTIVE);

        // auto assign task within boq to user
        taskService.findByBoq(boq).forEach(task -> taskService.assign(task, user));
        return assignRepository.save(boqAssign);
    }

    public boolean unAssign(Long id, Long userId) {
        var boq = getById(id);
        var user = userService.getById(userId);
        var boqAssign = assignRepository.findByBoqAndAppUser(boq, user).orElseThrow();

        // auto unAssign task
        taskService.findByBoq(boq).forEach(task -> taskService.unAssign(task, user));
        assignRepository.delete(boqAssign);
        return true;
    }
}