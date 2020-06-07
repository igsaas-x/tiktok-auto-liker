package com.construction.organization.payment.repository;

import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.subconstructor.domain.SubConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
    List<PaymentRequest> findAllBySubConstructor(final SubConstructor constructor);

    @Query(value = "select * from payment_request where is_submitted = :submitted and is_verified = :verified" +
            " and is_confirmed = :confirmed and is_reviewed = :reviewed and is_approved = :approved", nativeQuery = true)
    List<PaymentRequest> findPending(@Param("submitted") boolean submitted, @Param("verified") boolean verified,
                                     @Param("confirmed") boolean confirmed, @Param("reviewed") boolean reviewed,
                                     @Param("approved") boolean approved);
}