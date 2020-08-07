package com.construction.organization.payment.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class PaymentEntryDto {
    Long taskId;
    String taskName;
    String description;
    Long subConstructorId;
    BigDecimal requestAmount;
    BigDecimal approvedAmount;
}
