package com.construction.feature.contract.repository;

import com.construction.feature.contract.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
