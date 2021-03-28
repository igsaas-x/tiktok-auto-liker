package com.construction.feature.task.repository;

import com.construction.feature.task.domain.BOQAssign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BOQAssignRepository extends JpaRepository<BOQAssign, Long> {

    List<BOQAssign> findByBoq(String code);

    List<BOQAssign> findBySubConstructorId(Long subConstructorId);
}