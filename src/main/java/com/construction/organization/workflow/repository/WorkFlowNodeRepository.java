package com.construction.organization.workflow.repository;

import com.construction.organization.workflow.domain.WorkFlowNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkFlowNodeRepository extends JpaRepository<WorkFlowNode, Long> {
    List<WorkFlowNode> findAllByWorkFlowName(final String name);
}
