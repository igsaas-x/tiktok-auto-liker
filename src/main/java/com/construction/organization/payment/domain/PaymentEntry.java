package com.construction.organization.payment.domain;

import com.construction.feature.task.domain.Task;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.domain.VersionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentEntry extends VersionEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "payment_request_id")
    PaymentRequest paymentRequest;

    @ManyToOne
    @JoinColumn
    Task task;

    String description;

    BigDecimal requestAmount;

    BigDecimal approvedAmount;

    @ManyToOne
    @JoinColumn
    SubConstructor subConstructor;
}
