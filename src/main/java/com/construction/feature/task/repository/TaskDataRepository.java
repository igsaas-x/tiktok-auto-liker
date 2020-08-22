package com.construction.feature.task.repository;

import com.construction.feature.task.dto.TaskTemplateData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskDataRepository extends JpaRepository<TaskTemplateData, Long> {
    List<TaskTemplateData> findAllByFirstLevel(final boolean firstLevel);
}
