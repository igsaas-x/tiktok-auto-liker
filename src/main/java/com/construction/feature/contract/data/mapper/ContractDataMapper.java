package com.construction.feature.contract.data.mapper;

import com.construction.feature.contract.data.ContractData;
import com.construction.feature.contract.domain.Contract;
import com.construction.feature.task.repository.BOQRepository;
import com.construction.organization.subconstructor.repository.SubConstructorRepository;
import com.construction.persistence.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractDataMapper extends DtoMapper<Contract, ContractData> {

    private final SubConstructorRepository constructorRepository;
    private final BOQRepository boqRepository;

    @Override
    public Contract toEntity(ContractData dto) {
        var contract = super.toEntity(dto);
        if (dto.getSubConstructorId() != null) {
            var constructor = constructorRepository.findById(dto.getSubConstructorId()).orElse(null);
            contract.setSubConstructor(constructor);
        }
        if (dto.getBoqIds() != null) {
            var boqs = boqRepository.findAllById(dto.getBoqIds());
            contract.setBoqs(boqs);
        }

        if (dto.getPaymentSteps() != null) {
            contract.setTotalStep(dto.getPaymentSteps().size());
        }
        return contract;
    }
}
