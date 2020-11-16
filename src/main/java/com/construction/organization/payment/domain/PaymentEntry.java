package com.construction.organization.payment.domain;

import com.construction.feature.task.domain.Task;
import com.construction.persistence.converter.StringSetConverter;
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
import java.util.Set;

import static com.construction.organization.payment.domain.PaymentEntryStatus.OPEN;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentEntry extends VersionEntity {

    @Column(unique = true)
    String externalId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_request_id", nullable = false)
    PaymentRequest paymentRequest;

    @ManyToOne
    @JoinColumn
    Task task;

    String description;
    
    @Convert(converter = StringSetConverter.class)
    Set<String> attachment;

    @NotNull
    BigDecimal requestAmount;

    BigDecimal approvedAmount;

    @Enumerated(EnumType.STRING)
    PaymentEntryStatus status = PaymentEntryStatus.OPEN;

    @PrePersist
    private void prePersist() {
        if (status == null) {
            status = OPEN;
        }
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "task cannot be null");
        }
        if (requestAmount == null) {
            requestAmount = task.getTotalPrice();
        }
        if (requestAmount.compareTo(task.getTotalPrice()) > 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "request amount cannot greater than task total amount");
        }
    }
}
