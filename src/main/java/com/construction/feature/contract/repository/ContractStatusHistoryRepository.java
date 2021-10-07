package com.construction.feature.contract.repository;

import com.construction.feature.contract.domain.ContractStatusHistory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractStatusHistoryRepository extends JpaRepository<ContractStatusHistory, Long> {
    List<ContractStatusHistory> findAllByPaymentRequestId(Long id, Sort createdAt);
}
