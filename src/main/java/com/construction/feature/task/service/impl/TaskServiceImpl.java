package com.construction.feature.task.service.impl;

import com.construction.feature.task.dao.TaskRepository;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.dto.TaskDTO;
import com.construction.feature.task.mapper.TaskMapper;
import com.construction.feature.task.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskMapper mapper;
    private final TaskRepository repository;

    public TaskServiceImpl(TaskMapper mapper, TaskRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public TaskDTO save(TaskDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void save(List<TaskDTO> dtos) {
        repository.saveAll(mapper.toEntityList(dtos));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<TaskDTO> findById(Long id) {
        Optional<Task> entityOptional = repository.findById(id);
        return entityOptional.map(entity -> Optional.ofNullable(mapper.toDto(entity))).orElse(null);
    }

    @Override
    public List<TaskDTO> findAll() {
        return mapper.toDtoList((List<Task>) repository.findAll());
    }

    @Override
    public Page<TaskDTO> findAll(Pageable pageable) {
        Page<Task> entityPage = repository.findAll(pageable);
        List<TaskDTO> dtos = mapper.toDtoList(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public TaskDTO updateById(Long id) {
        Optional<TaskDTO> optionalDto = findById(id);
        return optionalDto.map(this::save).orElse(null);
    }
}