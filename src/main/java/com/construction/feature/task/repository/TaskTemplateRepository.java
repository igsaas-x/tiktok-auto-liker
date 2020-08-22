package com.construction.feature.task.repository;

import com.construction.feature.task.domain.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
    Optional<TaskTemplate> findByCode(final String code);

    List<TaskTemplate> findAllByParentId(final Long id);
}
