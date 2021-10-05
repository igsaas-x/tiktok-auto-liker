package com.construction.feature.contract.data;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentStepData {
    private int sequenceNumber;
    private float percentage;

    @NotNull
    private BigDecimal amount;
    private String note;
    private boolean paid;
}
