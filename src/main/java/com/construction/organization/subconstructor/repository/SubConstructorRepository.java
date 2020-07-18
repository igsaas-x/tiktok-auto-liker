package com.construction.organization.subconstructor.repository;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.domain.ObjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubConstructorRepository extends JpaRepository<SubConstructor, Long>, JpaSpecificationExecutor<SubConstructor> {
    List<SubConstructor> findAllByStatus(final ObjectStatus status);
    Page<SubConstructor> findAllByStatus(final ObjectStatus status, final Pageable pageable);
    Page<SubConstructor> findAllByCreatedById(final Long id, final Pageable pageable);
}