package com.construction.feature.contract.data;

import com.construction.organization.payment.domain.CommandType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractStatusHistoryData {

    @JsonProperty(value = "createdBy", access = JsonProperty.Access.READ_ONLY)
    private String createdByUserName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(value = "updatedBy", access = JsonProperty.Access.READ_ONLY)
    private String updatedByUserName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    CommandType commandType;

    String attachment;

    String comment;

    BigDecimal approvedAmount;
}
