package com.construction.feature.contract.data.mapper;

import com.construction.feature.contract.data.ContractPaymentRequestData;
import com.construction.feature.contract.domain.ContractPaymentRequest;
import com.construction.feature.contract.repository.ContractRepository;
import com.construction.feature.contract.repository.PaymentStepRepository;
import com.construction.persistence.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractPaymentRequestMapper extends DtoMapper<ContractPaymentRequest, ContractPaymentRequestData> {

    private final ContractRepository contractRepository;
    private final PaymentStepRepository paymentStepRepository;

    @Override
    public ContractPaymentRequest toEntity(ContractPaymentRequestData dto) {
        final var paymentRequest = super.toEntity(dto);
        if (dto.getContractId() != null) {
            contractRepository.findById(dto.getContractId()).ifPresent(paymentRequest::setContract);
        }
        if (dto.getPaymentStepId() != null) {
            paymentStepRepository.findById(dto.getPaymentStepId()).ifPresent(paymentRequest::setPaymentStep);
        }
        return paymentRequest;
    }
}
