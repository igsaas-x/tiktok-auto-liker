package com.construction.feature.house.repositories;

import com.construction.feature.house.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 服务类
 *
 * @author 申畅恒
 * @since 1.0.0
 */
@Repository
public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {

}