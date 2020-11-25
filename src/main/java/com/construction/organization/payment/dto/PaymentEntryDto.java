package com.construction.organization.payment.dto;

import com.construction.feature.task.dto.TaskDto;
import com.construction.organization.payment.domain.PaymentEntryStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class PaymentEntryDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    Long taskId;

    @JsonProperty(value = "subConstructorName", access = JsonProperty.Access.READ_ONLY)
    String paymentRequestSubConstructorEngFullName;

    @JsonProperty(value = "task", access = JsonProperty.Access.READ_ONLY)
    TaskDto taskDto;

    String description;

    @JsonProperty(value = "subConstructorId", access = JsonProperty.Access.READ_ONLY)
    Long paymentRequestSubConstructorId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long paymentRequestId;

    BigDecimal requestAmount;

    BigDecimal approvedAmount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    PaymentEntryStatus status;
}
