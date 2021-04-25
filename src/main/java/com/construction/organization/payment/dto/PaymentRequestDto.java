package com.construction.organization.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class PaymentRequestDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    String externalId;

    @JsonProperty(value = "createdBy", access = JsonProperty.Access.READ_ONLY)
    String createdByUserName;

    @JsonProperty(value = "updatedBy", access = JsonProperty.Access.READ_ONLY)
    String updatedByUserName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;

    LocalDate requestDate;

    Long subConstructorId;

    Long projectId;

    Long streetId;

    Long houseId;

    String invoiceNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String subConstructorEngFullName;

    @JsonProperty(value = "paymentEntries")
    List<PaymentEntryDto> entries;

    List<String> attachments;
}
