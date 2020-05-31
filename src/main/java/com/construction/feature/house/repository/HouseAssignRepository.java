package com.construction.feature.house.repository;

import com.construction.feature.house.domain.HouseAssign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseAssignRepository extends JpaRepository<HouseAssign, Long> {
    Optional<HouseAssign> findByHouseIdAndAppUserId(Long houseId, Long appUserId);
}
