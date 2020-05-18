package com.construction.feature.task.service;

import com.construction.feature.task.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskDTO save(TaskDTO dto);

    void save(List<TaskDTO> dtos);

    void deleteById(Long id);

    Optional<TaskDTO> findById(Long id);

    List<TaskDTO> findAll();

    Page<TaskDTO> findAll(Pageable pageable);

    TaskDTO updateById(Long id);
}