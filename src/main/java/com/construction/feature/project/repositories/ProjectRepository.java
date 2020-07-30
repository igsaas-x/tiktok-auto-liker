package com.construction.feature.project.repositories;

import com.construction.feature.project.domain.Project;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    Page<Project> findAllByCreatedBy(AppUser user, Pageable pageable);

    @Query(value = "select p from Project p, ProjectAssign pa where p.id = pa.project and pa.appUser.id = :userId " +
            "and pa.assignFor = com.construction.persistence.domain.AssignFor.VERIFY " +
            "and p.status = com.construction.persistence.domain.ObjectStatus.OPEN")
    Page<Project> findPendingForVerify(final Long userId, Pageable pageable);

    @Query(value = "select p from Project p, ProjectAssign pa where p.id = pa.project and pa.appUser.id = :userId " +
            "and pa.assignFor = com.construction.persistence.domain.AssignFor.APPROVE " +
            "and p.status = com.construction.persistence.domain.ObjectStatus.VERIFIED")
    Page<Project> findPendingForApprove(final Long userId, Pageable pageable);

    @Query(value = "select p from Project p, ProjectAssign pa where p.id = pa.project and pa.appUser.id = :userId " +
            "and ((pa.assignFor = com.construction.persistence.domain.AssignFor.VERIFY " +
            "and p.status = com.construction.persistence.domain.ObjectStatus.OPEN) or " +
            "(pa.assignFor = com.construction.persistence.domain.AssignFor.APPROVE " +
            "and p.status = com.construction.persistence.domain.ObjectStatus.VERIFIED))")
    Page<Project> findAllPending(final Long userId, Pageable pageable);

    @Query(value = "select p from Project p, ProjectAssign pa where p.id = pa.project and pa.appUser.id = :userId")
    Page<Project> findAssignedProject(final Long userId, Pageable pageable);
}