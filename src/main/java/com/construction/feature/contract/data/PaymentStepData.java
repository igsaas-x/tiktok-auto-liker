package com.construction.feature.contract.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentStepData {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    private int sequenceNumber;
    private float percentage;

    @NotNull
    private BigDecimal amount;
    private String note;
    private boolean paid;
}
