package com.construction.feature.house.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.construction.feature.house.domain.House;

/**
 * 服务类
 * @author 申畅恒
 * @since 1.0.0
 */
@Repository
public interface HouseRepository extends JpaRepository<House,Long>,JpaSpecificationExecutor<House>{

}