package com.construction.feature.contract.controller;

import com.construction.feature.contract.data.ContractData;
import com.construction.feature.contract.data.mapper.ContractDataMapper;
import com.construction.feature.contract.domain.Contract;
import com.construction.feature.contract.repository.ContractRepository;
import com.construction.feature.task.domain.BOQ;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.filter.FilterConfig;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.user.authorization.domain.ActionName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("contract")
@RequiredArgsConstructor
public class ContractController {

    private static final String ENTITY_NAME = "CONTRACT";

    private final ContractRepository repository;
    private final ContractDataMapper dataMapper;
    private final EntityDataMapper entityDataMapper;
    private final FilterConfig filterConfig;

    @PostMapping
    @Transactional
    @PreAuthorize("hasAuthority('CREATE_CONTRACT')")
    public ContractData create(@Valid @RequestBody ContractData contractData) {
        var contract = dataMapper.toEntity(contractData);
        if (contract.getSubConstructor() == null) {
            throw new ResourceNotFoundException(SubConstructor.class, contractData.getSubConstructorId());
        }
        if (contract.getBoqs().isEmpty()) {
            throw new ResourceNotFoundException(BOQ.class, contractData.getBoqIds().toString());
        }
        return dataMapper.apply(repository.save(contract));
    }

    @PutMapping("/{id}")
    @Transactional
    public ContractData update(@PathVariable Long id, @RequestBody ContractData contractData) {
        filterConfig.configureFilter(ActionName.UPDATE, ENTITY_NAME);
        var contract = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Contract.class, id));
        var sourceContract = dataMapper.toEntity(contractData);
        var newContract = entityDataMapper.mapObject(sourceContract, contract, Contract.class);
        return dataMapper.apply(repository.save(newContract));
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.DELETE, ENTITY_NAME);
        var contract = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Contract.class, id));
        repository.delete(contract);
        return Map.of("success", true);
    }

    @GetMapping("/{id}")
    public ContractData getOne(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.DELETE, ENTITY_NAME);
        var contract = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Contract.class, id));
        return dataMapper.apply(contract);
    }

    @GetMapping
    public Page<ContractData> getAll(Pageable pageable) {
        filterConfig.configureFilter(ActionName.DELETE, ENTITY_NAME);
        return repository.findAll(pageable).map(dataMapper);
    }
}
