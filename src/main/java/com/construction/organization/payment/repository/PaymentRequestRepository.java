package com.construction.organization.payment.repository;

import com.construction.organization.payment.domain.PaymentStatus;
import com.construction.organization.payment.domain.PaymentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {

    @Query("select pr from PaymentRequest pr join pr.entries pe where pe.status = :status")
    Page<PaymentRequest> getPendingPaymentRequest(@Param("status") final PaymentStatus status, Pageable pageable);
}