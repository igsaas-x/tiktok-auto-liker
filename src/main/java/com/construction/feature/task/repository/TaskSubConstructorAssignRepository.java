package com.construction.feature.task.repository;

import com.construction.feature.task.domain.TaskSubConstructorAssign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskSubConstructorAssignRepository extends JpaRepository<TaskSubConstructorAssign, Long> {

    List<TaskSubConstructorAssign> findAllByTaskId(final Long id);

    List<TaskSubConstructorAssign> findAllBySubConstructorId(final Long id);

    void deleteByTaskIdAndSubConstructorId(Long taskId, Long subConstructorId);
}
