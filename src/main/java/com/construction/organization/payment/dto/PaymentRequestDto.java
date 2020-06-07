package com.construction.organization.payment.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequestDto {
    Long taskId;
    String description;
    Long subConstructorId;
}
