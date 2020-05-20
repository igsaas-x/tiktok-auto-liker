package com.construction.organization.subconstructor.dao;

import com.construction.organization.subconstructor.domain.SubConstructor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubConstructorRepository extends PagingAndSortingRepository<SubConstructor, String> {
}