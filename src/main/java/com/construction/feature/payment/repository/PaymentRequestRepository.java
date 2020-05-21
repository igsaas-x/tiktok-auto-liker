package com.construction.feature.payment.repository;

import com.construction.feature.payment.domain.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
}