package com.construction.feature.street.service.impl;

import com.construction.feature.street.dao.StreetRepository;
import com.construction.feature.street.domain.Street;
import com.construction.feature.street.dto.StreetDTO;
import com.construction.feature.street.mapper.StreetMapper;
import com.construction.feature.street.service.StreetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StreetServiceImpl implements StreetService {
    private final StreetMapper mapper;
    private final StreetRepository repository;

    public StreetServiceImpl(StreetMapper mapper, StreetRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public StreetDTO save(StreetDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void save(List<StreetDTO> dtos) {
        repository.saveAll(mapper.toEntityList(dtos));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<StreetDTO> findById(Long id) {
        Optional<Street> entityOptional = repository.findById(id);
        return entityOptional.map(entity -> Optional.ofNullable(mapper.toDto(entity))).orElse(null);
    }

    @Override
    public List<StreetDTO> findAll() {
        return mapper.toDtoList((List<Street>) repository.findAll());
    }

    @Override
    public Page<StreetDTO> findAll(Pageable pageable) {
        Page<Street> entityPage = repository.findAll(pageable);
        List<StreetDTO> dtos = mapper.toDtoList(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public StreetDTO updateById(Long id) {
        Optional<StreetDTO> optionalDto = findById(id);
        return optionalDto.map(this::save).orElse(null);
    }
}