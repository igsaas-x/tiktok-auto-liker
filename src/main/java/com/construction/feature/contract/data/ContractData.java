package com.construction.feature.contract.data;

import com.construction.feature.task.dto.BOQData;
import com.construction.organization.subconstructor.data.SubConstructorData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractData {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    SubConstructorData subConstructor;

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

    @NotNull
    @Min(1)
    int totalStep;

    int paidStep;

    String note;
}
