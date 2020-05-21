package com.construction.feature.house.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.house.domain.House;
import com.construction.feature.house.repository.HouseRepository;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.service.EntityDataMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class HouseService {

    private final HouseRepository repository;
    private final EntityDataMapper dataMapper;
    private final ApplicationSecurityContext context;

    public HouseService(HouseRepository repository,
                        EntityDataMapper dataMapper,
                        ApplicationSecurityContext context) {
        this.repository = repository;
        this.dataMapper = dataMapper;
        this.context = context;
    }

    public House save(House house) {
        return repository.save(house);
    }

    public void deleteById(Long id) {
        var house = repository.findById(id).orElseThrow();
        repository.delete(house);
    }

    public House findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<House> findAll() {
        return repository.findAll();
    }

    public Page<House> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public House updateById(Long id, House source) {
        var target = repository.findById(id).orElseThrow();
        if (target.getStatus().equals(ObjectStatus.VERIFIED) || target.getStatus().equals(ObjectStatus.APPROVED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "object is not in updatable state");
        }
        target = dataMapper.mapObject(source, target, House.class);
        return repository.save(target);
    }

    public House verify(Long id) {
        var house = repository.findById(id).orElseThrow();
        if (!house.getStatus().equals(ObjectStatus.OPEN)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not in open state");
        }
        house.setStatus(ObjectStatus.VERIFIED);
        house.setVerifiedAt(LocalDateTime.now());
        house.setVerifiedBy(context.authenticatedUser());
        return repository.save(house);
    }

    public House approve(Long id) {
        var house = repository.findById(id).orElseThrow();
        if (!house.getStatus().equals(ObjectStatus.VERIFIED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not in open state");
        }
        house.setStatus(ObjectStatus.APPROVED);
        house.setApprovedAt(LocalDateTime.now());
        house.setApprovedBy(context.authenticatedUser());
        return repository.save(house);
    }
}