package com.construction.feature.task.repository;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.domain.TaskAssign;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskAssignRepository extends JpaRepository<TaskAssign, Long> {
    Optional<TaskAssign> findByTaskAndAppUser(Task task, AppUser user);
}
