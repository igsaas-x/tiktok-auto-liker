package com.construction.feature.task.repository;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.Task;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findAllByBoq(final BOQ boq);

    Page<Task> findAllByCreatedBy(final AppUser appUser, Pageable pageable);

    @Query(value = "select t.* from task t, task_assign ta where t.id = ta.task_id and ta.app_user_id = :userId " +
            "and ta.assign_for = :assignFor and t.status = :status", nativeQuery = true)
    List<Task> findPendingTask(Long userId, @Param("assignFor") String assignFor, @Param("status") String status);

    @Query(value = "select p from Task p, TaskAssign pa where p.id = pa.task and pa.appUser.id = :userId " +
            "and pa.assignFor = :assignFor and p.status = :status")
    Page<Task> findPendingTask(@Param("userId") Long userId,
                               @Param("assignFor") AssignFor assignFor,
                               @Param("status") ObjectStatus status, Pageable pageable);

    @Query(value = "select p from Task p, TaskAssign pa where p.id = pa.task and pa.appUser.id = :userId " +
            "and ((pa.assignFor = :assignFor and p.status = :status) " +
            "or (pa.assignFor = :assignFor1 and p.status = :status1))")
    Page<Task> findAllPendingTask(@Param("userId") Long userId,
                               @Param("assignFor") AssignFor assignFor,
                               @Param("status") ObjectStatus status,
                               @Param("assignFor1") AssignFor assignFor1,
                               @Param("status1") ObjectStatus status1, Pageable pageable);

    @Query(value = "select p from Task p, TaskAssign pa where p.id = pa.task and pa.appUser.id = :userId")
    Page<Task> findAssignedTask(Long userId, Pageable pageable);
}