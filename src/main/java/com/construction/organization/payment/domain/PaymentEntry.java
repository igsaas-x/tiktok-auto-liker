package com.construction.organization.payment.domain;

import com.construction.feature.task.domain.Task;
import com.construction.persistence.converter.StringSetConverter;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.domain.VersionEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Set;

import static com.construction.organization.payment.domain.PaymentStatus.OPEN;
import static com.construction.persistence.constants.NumberConstants.HUNDRED;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentEntry extends VersionEntity {

    @Column(unique = true)
    String externalId;

    String invoiceNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_request_id", nullable = false, updatable = false)
    PaymentRequest paymentRequest;

    @ManyToOne
    @JoinColumn(updatable = false)
    Task task;

    String description;

    @Convert(converter = StringSetConverter.class)
    Set<String> attachment;

    @Column(nullable = false)
    BigDecimal requestAmount;

    Float requestAmountAsPercent;

    BigDecimal approvedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PaymentStatus status = PaymentStatus.OPEN;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate paidOn;

    @PrePersist
    private void prePersist() {
        if (status == null) {
            status = OPEN;
        }
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "task cannot be null");
        } else {
            if (!ObjectStatus.APPROVED.equals(task.getStatus())) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "task is not approved");
            }
        }
        if (requestAmount == null && requestAmountAsPercent == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "requestAmount and requestAmountAsPercent cannot be both null");
        } else if (requestAmount != null && requestAmountAsPercent != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "requestAmount and requestAmountAsPercent cannot be both exist");
        } else if (requestAmount != null) {
            if (requestAmount.compareTo(task.getAvailableAmount()) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "request amount cannot greater than available amount");
            }
        } else {
            if (requestAmountAsPercent.compareTo(task.getAvailableAmountAsPercent()) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "request amount percent cannot greater than available amount as percent");
            }
            requestAmount = task.getTotalPrice()
                    .multiply(BigDecimal.valueOf(requestAmountAsPercent))
                    .divide(HUNDRED, RoundingMode.UNNECESSARY);
        }
    }
}
