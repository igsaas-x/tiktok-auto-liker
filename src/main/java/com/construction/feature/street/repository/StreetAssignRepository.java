package com.construction.feature.street.repository;

import com.construction.feature.street.domain.StreetAssign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreetAssignRepository extends JpaRepository<StreetAssign, Long> {
    Optional<StreetAssign> findByStreetIdAndAppUserId(Long streetId, Long appUserId);
}
