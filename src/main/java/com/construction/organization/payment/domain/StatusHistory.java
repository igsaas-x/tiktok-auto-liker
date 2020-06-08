package com.construction.organization.payment.domain;

import com.construction.persistence.domain.SimpleAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusHistory extends SimpleAuditingEntity {

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    PaymentRequest paymentRequest;

    @Enumerated(EnumType.STRING)
    CommandType commandType;

    String comment;
}
