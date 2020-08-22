package com.construction.organization.payment.repository;

import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.PaymentRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentEntryRepository extends JpaRepository<PaymentEntry, Long> {

    List<PaymentEntry> findByIdIn(final List<Long> ids);

    Page<PaymentEntry> findAllByPaymentRequestSubConstructorId(final Long id, final Pageable pageable);

    List<PaymentEntry> findAllByPaymentRequestId(final Long id);

    Page<PaymentEntry> findAllByStatus(final PaymentRequestStatus status, final Pageable pageable);

    Page<PaymentEntry> findAllByStatusNot(final PaymentRequestStatus status, final Pageable pageable);
}
