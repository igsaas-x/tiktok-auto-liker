package com.construction.organization.payment.domain;

import com.construction.feature.task.domain.Task;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.domain.SimpleAuditingEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Filter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Filter(name = "myObjectFilter", condition = "created_by = :id")
@Filter(name = "readableObjectFilter", condition = "created_by = :id")
public class PaymentRequest extends SimpleAuditingEntity {

    @ManyToOne
    @JoinColumn
    Task task;

    String description;

    @ManyToOne
    @JoinColumn
    SubConstructor subConstructor;

    @Enumerated(EnumType.STRING)
    PaymentRequestStatus status = PaymentRequestStatus.OPEN;
}
