package com.construction.feature.street.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.exception.UnAuthorizeException;
import com.construction.feature.FilterType;
import com.construction.feature.street.domain.Street;
import com.construction.feature.street.domain.StreetAssign;
import com.construction.feature.street.repository.StreetAssignRepository;
import com.construction.feature.street.repository.StreetRepository;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.EntityValidator;
import com.construction.persistence.utils.SFWhere;
import com.construction.user.authentication.service.AppUserService;
import com.construction.user.authorization.domain.ActionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StreetService {

    @Autowired
    private StreetRepository repository;
    @Autowired
    private EntityDataMapper dataMapper;
    @Autowired
    private EntityValidator<Street> validator;
    @Autowired
    private ApplicationSecurityContext context;
    @Autowired
    private StreetAssignRepository assignRepository;
    @Autowired
    private AppUserService userService;

    public Street save(Street dto) {
        return repository.save(dto);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Street getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Street.class, id));
    }

    public ResponseEntity<Object> search(Street street, Pageable pageable) {
        Page<Street> all = repository.findAll(SFWhere.and(street)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    public List<Street> getAssignedStreet() {
        return assignRepository.findAllByAppUser(context.authenticatedUser())
                .stream()
                .map(StreetAssign::getStreet)
                .collect(Collectors.toList());
    }

    public List<Street> getAll() {
        return repository.findAll();
    }

    public Page<Street> getPendingForVerify(Pageable pageable) {
        return repository.findPendingForVerify(context.authenticatedUser().getId(), pageable);
    }

    public Page<Street> getPendingForApprove(Pageable pageable) {
        return repository.findPendingForApprove(context.authenticatedUser().getId(), pageable);
    }

    public Page<Street> getAllPending(Pageable pageable) {
        return repository.findAllPending(context.authenticatedUser().getId(), pageable);
    }

    public Page<Street> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Street> getAll(Pageable pageable, FilterType type) {
        final var user = context.authenticatedUser();
        if (user == null) {
            throw new UnAuthorizeException();
        }
        switch (type) {
            case PENDING_FOR_APPROVE:
                return repository.findPendingForApprove(user.getId(), pageable);
            case PENDING_FOR_VERIFY:
                return repository.findPendingForVerify(user.getId(), pageable);
            case ASSIGNED:
                return repository.findAssignedStreet(user.getId(), pageable);
            case OWNED:
                return repository.findAllByCreatedBy(user, pageable);
            default:
                return repository.findAll(pageable);
        }
    }

    public Street update(Long id, final Street street) {
        var target = getById(id);
        validator.validateStatus(target, ActionName.UPDATE);
        target = dataMapper.mapObject(street, target, Street.class);
        return repository.save(target);
    }

    public StreetAssign assign(Long id, Long uid, AssignFor assignFor) {
        var street = getById(id);
        var user = userService.getById(uid);
        var streetAssign = new StreetAssign().setStreet(street);
        streetAssign.setAppUser(user);
        streetAssign.setCreatedAt(LocalDateTime.now());
        streetAssign.setCreatedBy(context.authenticatedUser());
        streetAssign.setAssignFor(assignFor);
        return assignRepository.save(streetAssign);
    }

    public void unAssign(Long id, Long uid) {
        var streetAssign = assignRepository.findByStreetIdAndAppUserId(id, uid).orElseThrow();
        assignRepository.delete(streetAssign);
    }

    public Street verify(Long id) {
        var target = getById(id);
        validator.validateStatus(target, ActionName.VERIFY);
        target.setStatus(ObjectStatus.VERIFIED);
        target.setVerifiedAt(LocalDateTime.now());
        target.setVerifiedBy(context.authenticatedUser());
        return repository.save(target);
    }

    public List<Street> verifyAll(List<Long> ids) {
        return ids.stream().map(this::verify).collect(Collectors.toList());
    }

    public Street approve(Long id) {
        var target = getById(id);
        validator.validateStatus(target, ActionName.APPROVE);
        target.setStatus(ObjectStatus.APPROVED);
        target.setApprovedAt(LocalDateTime.now());
        target.setApprovedBy(context.authenticatedUser());
        return repository.save(target);
    }

    public List<Street> approveAll(List<Long> ids) {
        return ids.stream().map(this::approve).collect(Collectors.toList());
    }

}