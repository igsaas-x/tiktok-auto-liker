package com.construction.feature.task.repository;

import com.construction.feature.task.domain.TaskSubConstructorAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskSubConstructorAssignRepository extends JpaRepository<TaskSubConstructorAssign, Long> {

    List<TaskSubConstructorAssign> findAllByTaskId(final Long id);

    List<TaskSubConstructorAssign> findAllBySubConstructorId(final Long id);

    void deleteByTaskIdAndSubConstructorId(Long taskId, Long subConstructorId);

    @Modifying
    @Query("delete from task_sub_constructor_assign where task_id = :id")
    void deleteWithTaskId(@Param("id") Long id);
}
