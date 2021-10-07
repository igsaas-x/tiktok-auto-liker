package com.construction.feature.contract.controller;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.exception.UnAuthorizeException;
import com.construction.feature.contract.data.ContractPaymentRequestData;
import com.construction.feature.contract.data.ContractStatusHistoryData;
import com.construction.feature.contract.data.mapper.ContractPaymentRequestMapper;
import com.construction.feature.contract.data.mapper.ContractStatusHistoryMapper;
import com.construction.feature.contract.domain.Contract;
import com.construction.feature.contract.domain.ContractPaymentRequest;
import com.construction.feature.contract.domain.PaymentStep;
import com.construction.feature.contract.repository.ContractPaymentRequestRepository;
import com.construction.feature.contract.service.ContractPaymentRequestService;
import com.construction.feature.contract.service.ContractStatusHistoryService;
import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentStatus;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("contract/payment")
@RequiredArgsConstructor
public class ContractPaymentRequestController {

    private final ContractPaymentRequestRepository repository;
    private final ContractPaymentRequestMapper mapper;
    private final EntityDataMapper entityDataMapper;
    private final ContractPaymentRequestService service;
    private final ContractStatusHistoryService historyService;
    private final ContractStatusHistoryMapper historyMapper;
    private final ApplicationSecurityContext securityContext;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PAYMENT')")
    public ContractPaymentRequestData create(@Valid @RequestBody ContractPaymentRequestData paymentRequestData) {
        var paymentRequest = mapper.toEntity(paymentRequestData);
        if (paymentRequest.getContract() == null) {
            throw new ResourceNotFoundException(Contract.class, paymentRequestData.getContractId());
        }
        if (!ObjectStatus.APPROVED.equals(paymentRequest.getContract().getStatus())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Contract is not approved, cannot create payment request");
        }
        if (paymentRequest.getPaymentStep() == null) {
            throw new ResourceNotFoundException(PaymentStep.class, paymentRequestData.getPaymentStepId());
        }
        return mapper.apply(repository.save(paymentRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PAYMENT')")
    public ContractPaymentRequestData update(@PathVariable Long id, @RequestBody ContractPaymentRequestData paymentRequestData) {
        var sourcePaymentRequest = mapper.toEntity(paymentRequestData);
        var targetPaymentRequest = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ContractPaymentRequest.class, id));
        if (!PaymentStatus.OPEN.equals(targetPaymentRequest.getStatus())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payment request is not in a status where it can be updated");
        }
        var paymentRequest = entityDataMapper.mapObject(sourcePaymentRequest, targetPaymentRequest, ContractPaymentRequest.class);
        return mapper.apply(repository.save(paymentRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PAYMENT')")
    public Map<String, Object> delete(@PathVariable Long id) {
        var paymentRequest = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ContractPaymentRequest.class, id));
        repository.delete(paymentRequest);
        return Map.of("success", true);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PAYMENT')")
    public ContractPaymentRequestData getOne(@PathVariable Long id) {
        var paymentRequest = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ContractPaymentRequest.class, id));
        return mapper.apply(paymentRequest);
    }

    @GetMapping("/{id}/history")
    @PreAuthorize("hasAuthority('READ_PAYMENT')")
    public List<ContractStatusHistoryData> getHistory(@PathVariable Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ContractPaymentRequest.class, id));
        return historyService.getByPaymentRequestId(id)
                .stream()
                .map(historyMapper)
                .collect(Collectors.toList());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PAYMENT')")
    public Page<ContractPaymentRequestData> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper);
    }

    @PostMapping("/{id}")
    public ContractPaymentRequestData command(@PathVariable Long id,
                                              @RequestParam CommandType command,
                                              @RequestParam(required = false) BigDecimal approveAmount,
                                              @RequestParam(value = "{attachment:.+}", required = false) String attachment,
                                              @RequestParam(required = false) String comment) {
        // check permission
        if (!securityContext.hasPermissionTo(String.format("%s_PAYMENT", command))) {
            throw new UnAuthorizeException();
        }
        return mapper.apply(service.processCommand(id, command, attachment, approveAmount, comment));
    }
}
