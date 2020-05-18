package com.construction.feature.task.service;

import com.construction.feature.task.dto.BOQDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BOQService {
    BOQDTO save(BOQDTO dto);

    void save(List<BOQDTO> dtos);

    void deleteById(Long id);

    Optional<BOQDTO> findById(Long id);

    List<BOQDTO> findAll();

    Page<BOQDTO> findAll(Pageable pageable);

    BOQDTO updateById(Long id);
}