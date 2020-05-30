package com.construction.feature.house.repository;

import com.construction.feature.house.domain.House;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findAllByCreatedBy(final AppUser user);
}