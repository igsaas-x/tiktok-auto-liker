package com.construction.feature.contract.domain;

import com.construction.organization.payment.domain.PaymentStatus;
import com.construction.persistence.domain.SimpleAuditingEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Filter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Filter(name = "myObjectFilter", condition = "created_by = :id")
@Filter(name = "readableObjectFilter", condition = "created_by = :id")
public class ContractPaymentRequest extends SimpleAuditingEntity {

    @ManyToOne
    @JoinColumn
    Contract contract;

    @ManyToOne
    @JoinColumn(name = "payment_step_id")
    PaymentStep paymentStep;

    @Column
    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @Column(columnDefinition = "text")
    String detail;
}
