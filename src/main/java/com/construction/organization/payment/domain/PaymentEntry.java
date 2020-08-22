package com.construction.organization.payment.domain;

import com.construction.feature.task.domain.Task;
import com.construction.persistence.domain.VersionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static com.construction.organization.payment.domain.PaymentRequestStatus.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentEntry extends VersionEntity {

    @Column(unique = true)
    String externalId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "payment_request_id")
    PaymentRequest paymentRequest;

    @ManyToOne
    @JoinColumn
    Task task;

    String description;

    @NotNull
    BigDecimal requestAmount;

    BigDecimal approvedAmount;

    @Enumerated(EnumType.STRING)
    PaymentRequestStatus status = PaymentRequestStatus.OPEN;

    @PrePersist
    private void prePersist() {
        if (status == null) {
            status = OPEN;
        }
        if (requestAmount == null) {
            requestAmount = task.getTotalPrice();
        }
        if (requestAmount.compareTo(task.getTotalPrice()) > 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "request amount cannot greater than task total amount");
        }
    }

    public void reject() {
        if (OPEN.equals(status) || APPROVED.equals(status) || PAID.equals(status)) {
            throw new RuntimeException("status cannot be rejected");
        }
        status = OPEN;
    }
}
