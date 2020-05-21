package com.construction.feature.task.service;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.persistence.service.EntityDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private EntityDataMapper dataMapper;

    public Task save(Task dto) {
        return repository.save(dto);
    }

    public void save(List<Task> dtos) {
        repository.saveAll(dtos);
    }

    public void deleteById(Long id) {
        var task = repository.findById(id).orElseThrow();
        repository.delete(task);
    }

    public Task findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Page<Task> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Task updateById(Long id, Task task) {
        var target = repository.findById(id).orElseThrow();
        target = dataMapper.mapObject(task, target, Task.class);
        return repository.save(target);
    }
}