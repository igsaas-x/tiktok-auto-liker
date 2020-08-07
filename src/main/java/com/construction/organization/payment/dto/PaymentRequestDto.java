package com.construction.organization.payment.dto;

import com.construction.organization.payment.domain.PaymentRequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"createdBy", "updatedBy", "createdAt", "updatedAt", "status"}, allowGetters = true)
public class PaymentRequestDto {
    Long id;
    List<PaymentEntryDto> entries;
    PaymentRequestStatus status;
    String createdBy;
    String updatedBy;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
