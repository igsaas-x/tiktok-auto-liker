package com.construction.basic.keyvalue.service.impl;

import com.construction.basic.keyvalue.dao.KeyValueRepository;
import com.construction.basic.keyvalue.domain.KeyValue;
import com.construction.basic.keyvalue.dto.KeyValueDto;
import com.construction.basic.keyvalue.dto.mapper.KeyValueMapper;
import com.construction.basic.keyvalue.service.KeyValueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KeyValueServiceImpl implements KeyValueService {
    private final KeyValueMapper mapper;
    private final KeyValueRepository repository;

    public KeyValueServiceImpl(KeyValueMapper mapper, KeyValueRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public KeyValueDto save(KeyValueDto dto) {
        return mapper.apply(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void save(List<KeyValueDto> dtos) {
        repository.saveAll(mapper.toEntityList(dtos));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<KeyValueDto> findById(Long id) {
        Optional<KeyValue> entityOptional = repository.findById(id);
        return entityOptional.map(entity -> Optional.ofNullable(mapper.apply(entity))).orElse(null);
    }

    @Override
    public List<KeyValueDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public Page<KeyValueDto> findAll(Pageable pageable) {
        Page<KeyValue> entityPage = repository.findAll(pageable);
        List<KeyValueDto> dtos = mapper.toDtoList(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public KeyValueDto updateById(Long id) {
        Optional<KeyValueDto> optionalDto = findById(id);
        return optionalDto.map(this::save).orElse(null);
    }
}