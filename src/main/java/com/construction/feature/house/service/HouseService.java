package com.construction.feature.house.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.house.domain.House;
import com.construction.feature.house.domain.HouseAssign;
import com.construction.feature.house.repository.HouseAssignRepository;
import com.construction.feature.house.repository.HouseRepository;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.domain.TaskAssign;
import com.construction.feature.task.repository.TaskAssignRepository;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.ObjectStatusValidator;
import com.construction.user.authentication.service.AppUserService;
import com.construction.user.authorization.domain.ActionName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HouseService {

    private final HouseRepository repository;
    private final TaskAssignRepository taskAssignRepository;
    private final EntityDataMapper dataMapper;
    private final ApplicationSecurityContext context;
    private final ObjectStatusValidator<House> validator;
    private final HouseAssignRepository assignRepository;
    private final AppUserService userService;

    public HouseService(HouseRepository repository,
                        TaskAssignRepository taskAssignRepository,
                        EntityDataMapper dataMapper,
                        ApplicationSecurityContext context,
                        ObjectStatusValidator<House> validator,
                        HouseAssignRepository assignRepository,
                        AppUserService userService) {
        this.repository = repository;
        this.taskAssignRepository = taskAssignRepository;
        this.dataMapper = dataMapper;
        this.context = context;
        this.validator = validator;
        this.assignRepository = assignRepository;
        this.userService = userService;
    }

    public House save(House house) {
        return repository.save(house);
    }

    public void deleteById(Long id) {
        var house = getById(id);
        repository.delete(house);
    }

    public House getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(House.class, id));
    }

    public List<House> findAll() {
        var houses = repository.findAll();
        houses.addAll(fromAssignedTask());
        return houses.stream().distinct().collect(Collectors.toList());
    }

    public Page<House> getPendingForVerify(Pageable pageable) {
        return repository.findPendingForVerify(context.authenticatedUser().getId(), pageable);
    }

    public Page<House> getPendingForApprove(Pageable pageable) {
        return repository.findPendingForApprove(context.authenticatedUser().getId(), pageable);
    }

    private List<House> fromAssignedTask() {
        var user = context.authenticatedUser();
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        var taskAssigns = taskAssignRepository.findAllByAppUser(user);
        return taskAssigns.stream()
                .map(TaskAssign::getTask)
                .filter(task -> task.getHouse() != null)
                .map(Task::getHouse)
                .collect(Collectors.toList());
    }

    public Page<House> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public House updateById(Long id, House source) {
        var target = getById(id);
        validator.validateStatus(target, ActionName.UPDATE);
        target = dataMapper.mapObject(source, target, House.class);
        return repository.save(target);
    }

    public HouseAssign assign(Long id, Long userId, AssignFor assignFor) {
        var house = getById(id);
        var user = userService.getById(userId);
        var houseAssign = new HouseAssign().setHouse(house);
        houseAssign.setAppUser(user);
        houseAssign.setAssignFor(assignFor);
        return assignRepository.save(houseAssign);
    }

    public void unAssign(Long id, Long userId) {
        var houseAssign = assignRepository.findByHouseIdAndAppUserId(id, userId).orElseThrow();
        assignRepository.delete(houseAssign);
    }

    public House verify(Long id) {
        var house = getById(id);
        validator.validateStatus(house, ActionName.VERIFY);
        house.setStatus(ObjectStatus.VERIFIED);
        house.setVerifiedAt(LocalDateTime.now());
        house.setVerifiedBy(context.authenticatedUser());
        return repository.save(house);
    }

    public List<House> verifyAll(List<Long> ids) {
        return ids.stream().map(this::verify).collect(Collectors.toList());
    }

    public House approve(Long id) {
        var house = getById(id);
        validator.validateStatus(house, ActionName.APPROVE);
        house.setStatus(ObjectStatus.APPROVED);
        house.setApprovedAt(LocalDateTime.now());
        house.setApprovedBy(context.authenticatedUser());
        return repository.save(house);
    }

    public List<House> approveAll(List<Long> ids) {
        return ids.stream().map(this::approve).collect(Collectors.toList());
    }
}