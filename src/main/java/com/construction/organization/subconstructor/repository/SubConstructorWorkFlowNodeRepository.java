package com.construction.organization.subconstructor.repository;

import com.construction.organization.subconstructor.domain.SubConstructorWorkFlowNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubConstructorWorkFlowNodeRepository extends JpaRepository<SubConstructorWorkFlowNode, Long> {
}
