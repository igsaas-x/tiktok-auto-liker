package com.construction.feature.house.repository;

import com.construction.feature.house.domain.TypeOfHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfHouseRepository extends JpaRepository<TypeOfHouse, Long> {
}