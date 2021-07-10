package com.construction.organization.payment.dto;

import com.construction.feature.task.dto.TaskDto;
import com.construction.organization.payment.domain.PaymentEntryStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class PaymentEntryDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    Long taskId;

    String invoiceNumber;

    @JsonProperty(value = "subConstructorName", access = JsonProperty.Access.READ_ONLY)
    String paymentRequestSubConstructorEngFullName;

    @JsonProperty(value = "task", access = JsonProperty.Access.READ_ONLY)
    TaskDto taskDto;

    String description;

    @JsonProperty(value = "subConstructorId", access = JsonProperty.Access.READ_ONLY)
    Long paymentRequestSubConstructorId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long paymentRequestId;

    @JsonProperty(value = "requestDate")
    LocalDateTime paymentRequestCreatedAt;

    Set<String> attachment;

    BigDecimal requestAmount;

    Float requestAmountAsPercent;

    BigDecimal approvedAmount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    PaymentEntryStatus status;
}
