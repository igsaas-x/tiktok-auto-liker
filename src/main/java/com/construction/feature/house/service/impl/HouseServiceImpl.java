package com.construction.feature.house.service.impl;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.house.dao.HouseRepository;
import com.construction.feature.house.domain.House;
import com.construction.feature.house.dto.HouseDTO;
import com.construction.feature.house.mapper.HouseMapper;
import com.construction.feature.house.service.HouseService;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.service.EntityDataMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class HouseServiceImpl implements HouseService {

    private final HouseMapper mapper;
    private final HouseRepository repository;
    private final EntityDataMapper dataMapper;
    private final ApplicationSecurityContext context;

    public HouseServiceImpl(HouseMapper mapper,
                            HouseRepository repository,
                            EntityDataMapper dataMapper,
                            ApplicationSecurityContext context) {
        this.mapper = mapper;
        this.repository = repository;
        this.dataMapper = dataMapper;
        this.context = context;
    }

    @Override
    public HouseDTO save(HouseDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void save(List<HouseDTO> dtos) {
        repository.saveAll(mapper.toEntityList(dtos));
    }

    @Override
    public void deleteById(Long id) {
        var house = repository.findById(id).orElseThrow();
        repository.delete(house);
    }

    @Override
    public HouseDTO findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow());
    }

    @Override
    public List<HouseDTO> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public Page<HouseDTO> findAll(Pageable pageable) {
        var entityPage = repository.findAll(pageable);
        var dtos = mapper.toDtoList(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public HouseDTO updateById(Long id, HouseDTO dto) {
        var target = repository.findById(id).orElseThrow();
        if (target.getStatus().equals(ObjectStatus.VERIFIED) || target.getStatus().equals(ObjectStatus.APPROVED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "object is not in updatable state");
        }
        var source = mapper.toEntity(dto);
        target = dataMapper.mapObject(source, target, House.class);
        return mapper.toDto(repository.save(target));
    }

    @Override
    public HouseDTO verify(Long id) {
        var house = repository.findById(id).orElseThrow();
        if (!house.getStatus().equals(ObjectStatus.OPEN)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not in open state");
        }
        house.setStatus(ObjectStatus.VERIFIED);
        house.setVerifiedAt(LocalDateTime.now());
        house.setVerifiedBy(context.authenticatedUser());
        return mapper.toDto(repository.save(house));
    }

    @Override
    public HouseDTO approve(Long id) {
        var house = repository.findById(id).orElseThrow();
        if (!house.getStatus().equals(ObjectStatus.VERIFIED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not in open state");
        }
        house.setStatus(ObjectStatus.APPROVED);
        house.setApprovedAt(LocalDateTime.now());
        house.setApprovedBy(context.authenticatedUser());
        return mapper.toDto(repository.save(house));
    }
}