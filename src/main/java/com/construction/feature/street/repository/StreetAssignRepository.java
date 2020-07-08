package com.construction.feature.street.repository;

import com.construction.feature.street.domain.Street;
import com.construction.feature.street.domain.StreetAssign;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StreetAssignRepository extends JpaRepository<StreetAssign, Long> {
    Optional<StreetAssign> findByStreetIdAndAppUserId(Long streetId, Long appUserId);
    List<StreetAssign> findAllByAppUser(final AppUser appUser);
    List<StreetAssign> findAllByStreet(final Street street);
}
