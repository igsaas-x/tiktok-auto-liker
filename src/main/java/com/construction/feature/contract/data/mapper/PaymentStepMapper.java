package com.construction.feature.contract.data.mapper;

import com.construction.feature.contract.data.PaymentStepData;
import com.construction.feature.contract.domain.PaymentStep;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentStepMapper extends DtoMapper<PaymentStep, PaymentStepData> {

}
