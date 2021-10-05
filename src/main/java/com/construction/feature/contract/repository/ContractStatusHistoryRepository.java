package com.construction.feature.contract.repository;

import com.construction.feature.contract.domain.ContractStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractStatusHistoryRepository extends JpaRepository<ContractStatusHistory, Long> {
}
