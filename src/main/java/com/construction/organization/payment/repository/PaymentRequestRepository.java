package com.construction.organization.payment.repository;

import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.domain.PaymentRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
    List<PaymentRequest> findAllBySubConstructorId(final Long id);

    List<PaymentRequest> findAllByStatus(final PaymentRequestStatus status);

    @Query(value = "select * from payment_request where status <> 'APPROVED'", nativeQuery = true)
    List<PaymentRequest> findAllPending();
}