package com.construction.feature.house.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.house.domain.House;
import com.construction.feature.house.repository.HouseRepository;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.ObjectStatusValidator;
import com.construction.user.authorization.domain.ActionName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HouseService {

    private final HouseRepository repository;
    private final EntityDataMapper dataMapper;
    private final ApplicationSecurityContext context;
    private final ObjectStatusValidator<House> validator;

    public HouseService(HouseRepository repository,
                        EntityDataMapper dataMapper,
                        ApplicationSecurityContext context,
                        ObjectStatusValidator<House> validator) {
        this.repository = repository;
        this.dataMapper = dataMapper;
        this.context = context;
        this.validator = validator;
    }

    public House save(House house) {
        return repository.save(house);
    }

    public void deleteById(Long id) {
        var house = repository.findById(id).orElseThrow();
        repository.delete(house);
    }

    public House getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<House> findAll() {
        return repository.findAll();
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