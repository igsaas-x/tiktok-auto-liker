package com.construction.feature.street.service;

import com.construction.feature.street.dto.StreetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StreetService {
    StreetDTO save(StreetDTO dto);

    void save(List<StreetDTO> dtos);

    void deleteById(Long id);

    Optional<StreetDTO> findById(Long id);

    List<StreetDTO> findAll();

    Page<StreetDTO> findAll(Pageable pageable);

    StreetDTO updateById(Long id);
}