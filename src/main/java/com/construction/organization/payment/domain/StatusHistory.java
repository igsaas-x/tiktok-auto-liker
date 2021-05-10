package com.construction.organization.payment.domain;

import com.construction.persistence.domain.VersionEntity;
import com.construction.user.authentication.domain.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusHistory extends VersionEntity {

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    PaymentEntry paymentEntry;

    @Enumerated(EnumType.STRING)
    CommandType commandType;

    @ManyToOne
    @JoinColumn(name = "created_by")
    AppUser createdBy;

    String attachment;

    LocalDateTime createdAt = LocalDateTime.now();

    String comment;

    BigDecimal originApprovedAmount;

    BigDecimal newApprovedAmount;
}
