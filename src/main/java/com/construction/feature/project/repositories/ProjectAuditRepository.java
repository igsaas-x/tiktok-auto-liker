package com.construction.feature.project.repositories;

import com.construction.feature.project.domain.ProjectAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 服务类
 *
 * @author chanheng hehehe
 * @since 1.0.0
 */
@Repository
public interface ProjectAuditRepository extends JpaRepository<ProjectAudit, Long>, JpaSpecificationExecutor<ProjectAudit> {

}