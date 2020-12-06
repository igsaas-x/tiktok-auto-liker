package com.construction.feature.address.repository;

import com.construction.feature.address.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByParentId(final Long id);
}
