package com.construction.feature.task.repository;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.Task;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.domain.ObjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByBoq(final BOQ boq);

    @Query(value = "select t.* from task t, task_assign ta where t.id = ta.task_id and ta.app_user_id = :userId " +
            "and ta.assign_for = :assignFor and t.status = :status", nativeQuery = true)
    List<Task> getPendingTask(Long userId, @Param("assignFor") String assignFor, @Param("status") String status);
}