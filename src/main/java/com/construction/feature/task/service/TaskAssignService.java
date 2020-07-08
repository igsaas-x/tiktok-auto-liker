package com.construction.feature.task.service;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.repository.TaskAssignRepository;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.persistence.dto.AssignedDto;
import com.construction.persistence.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskAssignService {

    private final TaskAssignRepository repository;
    private final TaskRepository taskRepository;

    public List<AssignedDto> getAssignedUser(final Long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Task.class, id));
        return repository.findAllByTask(task)
                .stream()
                .map(AssignedDto::fromEntity)
                .collect(Collectors.toList());
    }
}
