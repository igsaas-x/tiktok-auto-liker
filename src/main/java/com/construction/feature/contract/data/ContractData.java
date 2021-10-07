package com.construction.feature.contract.data;

import com.construction.feature.task.dto.BOQData;
import com.construction.organization.subconstructor.data.SubConstructorData;
import com.construction.organization.subconstructor.data.SubConstructorDto;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.domain.SimpleObjectStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractData {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ObjectStatus status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    SubConstructorDto subConstructor;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Long subConstructorId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Set<BOQData> boqs;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Long> boqIds;

    @NotNull
    @Size(min = 1)
    Set<PaymentStepData> paymentSteps;

    @NotNull
    BigDecimal totalAmount;

    BigDecimal paidAmount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    int totalStep;

    int paidStep;

    String note;
}
