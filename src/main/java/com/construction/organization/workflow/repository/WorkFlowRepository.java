package com.construction.organization.workflow.repository;

import com.construction.organization.workflow.domain.WorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkFlowRepository extends JpaRepository<WorkFlow, Long> {
    Optional<WorkFlow> findByName(final String name);
}
