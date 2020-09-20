package com.construction.basic.keyvalue.service;

import com.construction.basic.keyvalue.dto.KeyValueDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface KeyValueService {
    KeyValueDto save(KeyValueDto dto);

    void save(List<KeyValueDto> dtos);

    void deleteById(Long id);

    Optional<KeyValueDto> findById(Long id);

    List<KeyValueDto> findAll();

    Page<KeyValueDto> findAll(Pageable pageable);

    KeyValueDto updateById(Long id);
}