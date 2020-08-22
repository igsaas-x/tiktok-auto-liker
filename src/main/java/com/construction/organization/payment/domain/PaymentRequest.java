package com.construction.organization.payment.domain;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.domain.SimpleAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Filter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Filter(name = "myObjectFilter", condition = "created_by = :id")
@Filter(name = "readableObjectFilter", condition = "created_by = :id")
public class PaymentRequest extends SimpleAuditingEntity {

    @Column(unique = true)
    String externalId;

    @ManyToOne
    @JoinColumn
    SubConstructor subConstructor;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name = "payment_request_id")
    List<PaymentEntry> entries;
}
