package com.construction.organization.subconstructor.service;

import com.construction.organization.subconstructor.dto.SubConstructorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubConstructorService {
    SubConstructorDTO save(SubConstructorDTO dto);

    void save(List<SubConstructorDTO> dtos);

    void deleteById(String id);

    Optional<SubConstructorDTO> findById(String id);

    List<SubConstructorDTO> findAll();

    Page<SubConstructorDTO> findAll(Pageable pageable);

    SubConstructorDTO updateById(SubConstructorDTO dto);
}