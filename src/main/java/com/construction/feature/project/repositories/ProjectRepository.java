package com.construction.feature.project.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.construction.feature.project.domain.Project;

/**
 * 服务类
 * @author chanheng hehehe
 * @since 1.0.0
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project,Long>,JpaSpecificationExecutor<Project>{

}