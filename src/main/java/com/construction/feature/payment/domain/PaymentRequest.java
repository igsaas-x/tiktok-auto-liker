package com.construction.feature.payment.domain;

import com.construction.feature.task.domain.Task;
import com.construction.persistence.domain.AuditingEntity;
import com.construction.user.authentication.domain.AppUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class PaymentRequest extends AuditingEntity {

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private AppUser reviewedBy;

    private LocalDateTime reviewedAt;

    @ManyToOne
    @JoinColumn
    private Task task;

    private String description;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private AppUser receiver;
}
