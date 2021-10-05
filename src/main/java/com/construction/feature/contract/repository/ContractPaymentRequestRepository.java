package com.construction.feature.contract.repository;

import com.construction.feature.contract.domain.ContractPaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractPaymentRequestRepository extends JpaRepository<ContractPaymentRequest, Long> {
}
