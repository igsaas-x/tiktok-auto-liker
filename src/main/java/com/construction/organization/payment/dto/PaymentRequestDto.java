package com.construction.organization.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"createdBy", "updatedBy", "createdAt", "updatedAt"}, allowGetters = true)
public class PaymentRequestDto {
    Long id;
    Long taskId;
    private String taskName;
    String description;
    Long subConstructorId;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
