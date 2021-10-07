package com.construction.feature.contract.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.contract.domain.Contract;
import com.construction.feature.contract.repository.ContractRepository;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.utils.EntityValidator;
import com.construction.user.authorization.domain.ActionName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repository;
    private final ApplicationSecurityContext context;
    private final EntityValidator<Contract> validator;

    public List<Contract> verifyAll(List<Long> ids) {
        var contracts = repository.findAllById(ids);
        contracts.forEach(contract -> {
            validator.validateStatus(contract, ActionName.VERIFY);
            contract.setStatus(ObjectStatus.VERIFIED);
            contract.setVerifiedBy(context.authenticatedUser());
            contract.setVerifiedAt(LocalDateTime.now());
        });
        return repository.saveAll(contracts);
    }

    public List<Contract> approveAll(List<Long> ids) {
        var contracts = repository.findAllById(ids);
        contracts.forEach(contract -> {
            validator.validateStatus(contract, ActionName.APPROVE);
            contract.setStatus(ObjectStatus.APPROVED);
            contract.setApprovedBy(context.authenticatedUser());
            contract.setApprovedAt(LocalDateTime.now());
        });
        return repository.saveAll(contracts);
    }
}
