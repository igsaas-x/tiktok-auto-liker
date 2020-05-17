package com.construction.feature.project.repositories;

import com.construction.feature.project.domain.Project;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    Page<Project> findAll(Pageable pageable);

    Page<Project> findAllByCreatedBy(AppUser user, Pageable pageable);
}