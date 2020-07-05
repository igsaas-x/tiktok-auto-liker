package com.construction.feature.task.repository;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.Task;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByBoq(final BOQ boq);

    Page<Task> findAllByCreatedBy(final AppUser appUser, Pageable pageable);

    @Query(value = "select t.* from task t, task_assign ta where t.id = ta.task_id and ta.app_user_id = :userId " +
            "and ta.assign_for = :assignFor and t.status = :status", nativeQuery = true)
    List<Task> findPendingTask(Long userId, @Param("assignFor") String assignFor, @Param("status") String status);

    @Query(value = "select t.* from task t, task_assign ta where t.id = ta.task_id and ta.app_user_id = :userId " +
            "and ta.assign_for = :assignFor and t.status = :status", nativeQuery = true)
    Page<Task> findPendingTask(Long userId, @Param("assignFor") String assignFor, @Param("status") String status, Pageable pageable);

    @Query(value = "select t.* from task t, task_assign ta where t.id = ta.task_id and ta.app_user_id = :userId", nativeQuery = true)
    Page<Task> findAssignedTask(Long userId, Pageable pageable);
}