package com.construction.feature.street.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.construction.feature.street.domain.Street;

@Repository
public interface StreetRepository extends JpaRepository<Street,Long>{

}