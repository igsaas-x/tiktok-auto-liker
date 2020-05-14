package com.construction.feature.task.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.construction.feature.task.domain.Task;

/**
 * 服务类
 * @author heng
 * @since 1.0.0
 */
@Repository
public interface TaskRepository extends JpaRepository<Task,Long>,JpaSpecificationExecutor<Task>{

}