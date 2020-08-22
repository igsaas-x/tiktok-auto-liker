package com.construction.organization.payment.repository;

import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.domain.PaymentRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {

}