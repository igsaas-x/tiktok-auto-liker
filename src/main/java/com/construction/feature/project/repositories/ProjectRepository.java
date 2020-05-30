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

    Page<Project> findAll(Pageable pageable);

    Page<Project> findAllByCreatedBy(AppUser user, Pageable pageable);

    @Query(value = "select p.* from project p, project_assign pa " +
            "where pa.assign_for = 'VERIFY' and p.id = pa.project_id and pa.app_user_id = :userId and p.status = 'OPEN'", nativeQuery = true)
    Page<Project> findPendingForVerify(final Long userId, Pageable pageable);

    @Query(value = "select p.* from project p, project_assign pa " +
            "where pa.assign_for = 'APPROVE' and p.id = pa.project_id and pa.app_user_id = :userId and p.status = 'VERIFIED'", nativeQuery = true)
    Page<Project> findPendingForApprove(final Long userId, Pageable pageable);
}