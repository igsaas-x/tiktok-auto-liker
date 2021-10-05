package com.construction.feature.contract.repository;

import com.construction.feature.contract.domain.PaymentStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStepRepository extends JpaRepository<PaymentStep, Long> {
}
