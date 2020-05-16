package com.construction.feature.payment.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.construction.feature.payment.domain.PaymentRequest;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest,Long>,JpaSpecificationExecutor<PaymentRequest>{

}