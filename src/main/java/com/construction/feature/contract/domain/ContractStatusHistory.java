package com.construction.feature.contract.domain;

import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.persistence.domain.SimpleAuditingEntity;
import com.construction.persistence.domain.VersionEntity;
import com.construction.user.authentication.domain.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractStatusHistory extends SimpleAuditingEntity {

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    ContractPaymentRequest paymentRequest;

    @Enumerated(EnumType.STRING)
    CommandType commandType;

    String attachment;

    String comment;

    BigDecimal originApprovedAmount;

    BigDecimal newApprovedAmount;
}
