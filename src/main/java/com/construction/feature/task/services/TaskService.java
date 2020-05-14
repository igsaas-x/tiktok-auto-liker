package com.construction.feature.task.services;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.repositories.TaskRepository;
import com.construction.persistence.utils.SFWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public ResponseEntity<Object> search(Task task, Pageable pageable) {
        Page<Task> all = taskRepository.findAll(SFWhere.and(task)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
