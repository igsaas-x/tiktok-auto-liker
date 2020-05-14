package com.construction.feature.project.repositories;

import com.construction.feature.project.domain.ProjectAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAuditRepository extends JpaRepository<ProjectAudit, Long>, JpaSpecificationExecutor<ProjectAudit> {

}