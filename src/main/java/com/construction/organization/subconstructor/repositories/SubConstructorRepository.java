package com.construction.organization.subconstructor.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.construction.organization.subconstructor.domain.SubConstructor;

/**
 * 服务类
 * @author chanheng seang
 * @since 1.0.0
 */
@Repository
public interface SubConstructorRepository extends JpaRepository<SubConstructor,Long>,JpaSpecificationExecutor<SubConstructor>{

}