package com.construction.organization.payment.dto;

import com.construction.organization.payment.domain.PaymentRequestStatus;
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
    Long id;
    Long taskId;
    String taskName;
    String description;
    @JsonProperty(value = "subConstructorId", access = JsonProperty.Access.READ_ONLY)
    Long paymentRequestSubConstructorId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long paymentRequestId;
    BigDecimal requestAmount;
    BigDecimal approvedAmount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    PaymentRequestStatus status;
}
