package com.construction.feature.task.repository;

import com.construction.feature.task.dto.TaskData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDataRepository extends JpaRepository<TaskData, Long> {
    
}
