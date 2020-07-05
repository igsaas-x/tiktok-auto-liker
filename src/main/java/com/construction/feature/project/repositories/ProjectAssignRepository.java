package com.construction.feature.project.repositories;

import com.construction.feature.project.domain.Project;
import com.construction.feature.project.domain.ProjectAssign;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectAssignRepository extends JpaRepository<ProjectAssign, Long> {
    Optional<ProjectAssign> findByProjectAndAppUser(Project project, AppUser appUser);
    List<ProjectAssign> findAllByAppUser(final AppUser appUser);
}
