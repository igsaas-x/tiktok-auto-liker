package com.construction.feature.contract.repository;

import com.construction.feature.contract.domain.Contract;
import com.construction.feature.contract.domain.ContractPaymentRequest;
import com.construction.feature.contract.domain.PaymentStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractPaymentRequestRepository extends JpaRepository<ContractPaymentRequest, Long> {

    List<ContractPaymentRequest> findAllByPaymentStep(PaymentStep paymentStep);

    List<ContractPaymentRequest> findAllByContractIn(List<Contract> contracts);
}
