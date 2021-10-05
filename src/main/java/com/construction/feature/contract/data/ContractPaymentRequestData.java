package com.construction.feature.contract.data;

import com.construction.organization.payment.domain.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractPaymentRequestData {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    ContractData contract;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Long contractId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    PaymentStepData paymentStep;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Long paymentStepId;

    PaymentStatus status;

    String detail;
}
