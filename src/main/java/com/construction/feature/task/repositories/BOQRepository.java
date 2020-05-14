package com.construction.feature.task.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.construction.feature.task.domain.BOQ;

/**
 * 服务类
 * @author heng
 * @since 1.0.0
 */
@Repository
public interface BOQRepository extends JpaRepository<BOQ,Long>,JpaSpecificationExecutor<BOQ>{

}