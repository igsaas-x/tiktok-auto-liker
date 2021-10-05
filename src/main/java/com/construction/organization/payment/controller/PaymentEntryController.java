package com.construction.organization.payment.controller;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.exception.UnAuthorizeException;
import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.PaymentStatus;
import com.construction.organization.payment.domain.StatusHistory;
import com.construction.organization.payment.dto.PaymentEntryDto;
import com.construction.organization.payment.dto.mapper.PaymentEntryMapper;
import com.construction.organization.payment.service.PaymentEntryService;
import com.construction.organization.payment.service.StatusHistoryService;
import com.construction.persistence.dto.IdList;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/payment-entry")
@RequiredArgsConstructor
public class PaymentEntryController {

    static final List<CommandType> ALLOWED_PENDING_FOR = Arrays.asList(CommandType.values());
    static final String ALLOWED_PARAM = "SUBMIT/VERIFY/CONFIRM/REVIEW/APPROVE/CASH-OUT/REJECT";

    private final PaymentEntryService service;
    private final PaymentEntryMapper mapper;
    private final StatusHistoryService historyService;
    private final ApplicationSecurityContext securityContext;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PAYMENT')")
    public PaymentEntryDto getById(@PathVariable("id") Long id) {
        return mapper.apply(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PAYMENT')")
    public List<PaymentEntryDto> getByAll() {
        return service.getAll().stream().map(mapper).collect(Collectors.toList());
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('READ_PAYMENT')")
    public Page<PaymentEntryDto> getByAllPaged(Pageable pageable) {
        return service.getAll(pageable).map(mapper);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('READ_PAYMENT')")
    public List<PaymentEntryDto> search(final Long boqId,
                                        final Long projectId,
                                        final Long streetId,
                                        final Long houseId,
                                        final Long subConstructorId,
                                        final PaymentStatus status,
                                        final Pageable pageable) {
        return service.search(boqId, projectId, streetId, houseId, subConstructorId, status, pageable)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('READ_PAYMENT')")
    public Page<PaymentEntryDto> getPending(@RequestParam CommandType pendingFor, Pageable pageable) {
        return service.findPendingFor(pendingFor, pageable).map(mapper);
    }

    @ApiOperation("Submit command to payment request. Parameters are:" + ALLOWED_PARAM)
    @PutMapping("/command")
    public Map<String, Object> handleCommand(@RequestBody IdList ids,
                                             @RequestParam CommandType command,
                                             @RequestParam(required = false) BigDecimal approveAmount,
                                             @RequestParam(value = "{attachment:.+}", required = false) String attachment,
                                             @RequestParam(required = false) String comment) {
        if (!ALLOWED_PENDING_FOR.contains(command)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unsupported 'command', supported commands are: " + ALLOWED_PARAM);
        }

        log.info("approve amount is: " + approveAmount);

        // check permission
        if (!securityContext.hasPermissionTo(String.format("%s_PAYMENT", command))) {
            throw new UnAuthorizeException();
        }

        service.getByAllId(ids.getIds()).forEach(entry -> service.handleCommand(entry, command, attachment, approveAmount, comment));
        return Map.of("success", true);
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PAYMENT')")
    public PaymentEntry update(@PathVariable Long id, @RequestBody PaymentEntryDto dto) {
        return service.update(id, mapper.toEntity(dto));
    }

    @ApiOperation("delete by Id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PAYMENT')")
    public Map<String, Object> delete(@PathVariable Long id) {
        final var result = service.delete(id);
        return Map.of("success", result);
    }

    @ApiOperation("history")
    @GetMapping("/{id}/history")
    @PreAuthorize("hasAuthority('READ_PAYMENT')")
    public List<StatusHistory> getPaymentRequestHistory(@PathVariable Long id) {
        return historyService.getByPaymentEntryId(id);
    }

    @GetMapping("/by-subconstrucor/{id}")
    @PreAuthorize("hasAuthority('READ_PAYMENT')")
    public Page<PaymentEntryDto> getBySubConstructor(@PathVariable Long id, Pageable pageable) {
        return service.getBySubConstructorId(id, PaymentStatus.APPROVED, pageable).map(mapper);
    }
}
