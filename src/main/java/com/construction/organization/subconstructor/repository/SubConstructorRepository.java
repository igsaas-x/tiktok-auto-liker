package com.construction.organization.subconstructor.repository;

import com.construction.organization.subconstructor.domain.SubConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubConstructorRepository extends JpaRepository<SubConstructor, Long> {

}