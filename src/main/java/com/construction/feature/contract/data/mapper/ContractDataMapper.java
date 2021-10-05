package com.construction.feature.contract.data.mapper;

import com.construction.feature.contract.data.ContractData;
import com.construction.feature.contract.domain.Contract;
import com.construction.feature.task.repository.BOQRepository;
import com.construction.organization.subconstructor.repository.SubConstructorRepository;
import com.construction.persistence.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContractDataMapper extends DtoMapper<Contract, ContractData> {

    private final SubConstructorRepository constructorRepository;
    private final BOQRepository boqRepository;
    private final PaymentStepMapper paymentStepMapper;

    @Override
    public Contract toEntity(ContractData dto) {
        var constructor = constructorRepository.findById(dto.getSubConstructorId()).orElse(null);
        var boqs = boqRepository.findAllById(dto.getBoqIds());
        var paymentStep = dto.getPaymentSteps().stream()
                .map(paymentStepMapper::toEntity)
                .collect(Collectors.toList());
        var contract = super.toEntity(dto);
        contract.setPaymentSteps(paymentStep);
        contract.setBoqs(boqs);
        contract.setSubConstructor(constructor);
        return contract;
    }
}
