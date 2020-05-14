package com.construction.feature.payment.domain;

import com.construction.feature.task.domain.Task;
import com.construction.persistence.domain.AuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class PaymentRequest extends AuditingEntity {

    @ManyToOne
    @JoinColumn
    private Task task;
}
