package com.construction.feature.task.repository;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByBoq(final BOQ boq);
}