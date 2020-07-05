package com.construction.organization.subconstructor.repository;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.domain.ObjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubConstructorRepository extends JpaRepository<SubConstructor, Long>, JpaSpecificationExecutor<SubConstructor> {
    List<SubConstructor> findAllByStatus(final ObjectStatus status);
}