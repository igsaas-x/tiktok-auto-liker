package com.construction.feature.street.repository;

import com.construction.feature.street.domain.Street;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long>, JpaSpecificationExecutor<Street> {

    Page<Street> findAllByCreatedBy(final AppUser appUser, final Pageable pageable);

    @Query(value = "select p from Street p, StreetAssign pa where p.id = pa.street and pa.appUser.id = :userId " +
            "and pa.assignFor = com.construction.persistence.domain.AssignFor.VERIFY " +
            "and p.status = com.construction.persistence.domain.ObjectStatus.OPEN")
    Page<Street> findPendingForVerify(Long userId, Pageable pageable);

    @Query(value = "select p from Street p, StreetAssign pa where p.id = pa.street and pa.appUser.id = :userId " +
            "and pa.assignFor = com.construction.persistence.domain.AssignFor.APPROVE " +
            "and p.status = com.construction.persistence.domain.ObjectStatus.VERIFIED")
    Page<Street> findPendingForApprove(Long userId, Pageable pageable);

    @Query(value = "select p from Street p, StreetAssign pa where p.id = pa.street and pa.appUser.id = :userId")
    Page<Street> findAssignedStreet(Long userId, Pageable pageable);
}