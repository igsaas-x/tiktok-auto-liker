package com.construction.basic.config.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.construction.basic.config.domain.SystemConfig;

/**
 * 服务类
 * @author chanheng
 * @since 1.0.0
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig,Long>,JpaSpecificationExecutor<SystemConfig>{

}