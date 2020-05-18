package com.construction.feature.house.service.impl;

import com.construction.feature.house.dao.HouseRepository;
import com.construction.feature.house.domain.House;
import com.construction.feature.house.dto.HouseDTO;
import com.construction.feature.house.mapper.HouseMapper;
import com.construction.feature.house.service.HouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HouseServiceImpl implements HouseService {
    private final HouseMapper mapper;
    private final HouseRepository repository;

    public HouseServiceImpl(HouseMapper mapper, HouseRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
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
        repository.deleteById(id);
    }

    @Override
    public Optional<HouseDTO> findById(Long id) {
        Optional<House> entityOptional = repository.findById(id);
        return entityOptional.map(entity -> Optional.ofNullable(mapper.toDto(entity))).orElse(null);
    }

    @Override
    public List<HouseDTO> findAll() {
        return mapper.toDtoList((List<House>) repository.findAll());
    }

    @Override
    public Page<HouseDTO> findAll(Pageable pageable) {
        Page<House> entityPage = repository.findAll(pageable);
        List<HouseDTO> dtos = mapper.toDtoList(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public HouseDTO updateById(Long id) {
        Optional<HouseDTO> optionalDto = findById(id);
        return optionalDto.map(this::save).orElse(null);
    }
}