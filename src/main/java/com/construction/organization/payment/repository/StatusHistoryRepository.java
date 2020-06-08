package com.construction.organization.payment.repository;

import com.construction.organization.payment.domain.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {
    List<StatusHistory> findAllByPaymentRequestId(final Long id);
}
