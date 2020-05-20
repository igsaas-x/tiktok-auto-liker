package com.construction.feature.house.service;

import com.construction.feature.house.dto.HouseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HouseService {
    HouseDTO save(HouseDTO dto);

    void save(List<HouseDTO> dtos);

    void deleteById(Long id);

    HouseDTO findById(Long id);

    List<HouseDTO> findAll();

    Page<HouseDTO> findAll(Pageable pageable);

    HouseDTO updateById(Long id, HouseDTO dto);

    HouseDTO verify(Long id);

    HouseDTO approve(Long id);
}