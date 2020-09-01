package com.construction.organization.payment.controller;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentEntryStatus;
import com.construction.organization.payment.dto.PaymentEntryDto;
import com.construction.organization.payment.dto.PaymentRequestDto;
import com.construction.organization.payment.dto.mapper.PaymentEntryMapper;
import com.construction.organization.payment.dto.mapper.PaymentRequestMapper;
import com.construction.organization.payment.service.PaymentEntryService;
import com.construction.organization.payment.service.PaymentRequestService;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/payment-request")
@RestController
@Api(tags = "PaymentRequest API")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentRequestController {

    static final List<CommandType> ALLOWED_PENDING_FOR = Arrays.asList(CommandType.values());
    static final String ALLOWED_PARAM = "SUBMIT/VERIFY/CONFIRM/REVIEW/APPROVE/CASH-OUT/REJECT";

    final PaymentRequestService service;
    final PaymentRequestMapper requestMapper;
    final PaymentEntryService entryService;
    final PaymentEntryMapper entryMapper;
    final FilterConfig filterConfig;
    final ApplicationSecurityContext context;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_PAYMENT')")
    public PaymentRequestDto save(@RequestBody PaymentRequestDto dto) {
        return requestMapper.apply(service.save(requestMapper.toEntity(dto)));
    }

    @GetMapping("/{id}")
    public PaymentRequestDto findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "payment");
        return requestMapper.apply(service.getById(id));
    }

    @PutMapping("/{id}")
    public PaymentRequestDto updateById(@PathVariable("id") Long id, @RequestBody PaymentRequestDto dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "payment");
        return requestMapper.apply(service.update(id, requestMapper.toEntity(dto)));
    }

    @PutMapping("/{id}/add-entries")
    public PaymentRequestDto addEntries(@PathVariable("id") Long id, @RequestBody List<PaymentEntryDto> dtos) {
        filterConfig.configureFilter(ActionName.UPDATE, "payment");
        final var entries = dtos.stream().map(entryMapper::toEntity).collect(Collectors.toList());
        return requestMapper.apply(service.addEntries(id, entries));
    }

    @PutMapping("/{id}/remove-entries")
    public boolean removeEntries(@PathVariable("id") Long id, @RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.DELETE, "payment");
        ids.getIds().forEach(entryService::delete);
        return true;
    }

    @ApiOperation("delete by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "payment");
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public Page<PaymentRequestDto> list(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "payment");
        return service.getAll(pageable).map(requestMapper);
    }

    @ApiOperation("Find data pending. Parameters are:" + ALLOWED_PARAM)
    @GetMapping("/pending")
    public Page<PaymentRequestDto> getPendingPaymentRequest(@RequestParam CommandType pendingFor, Pageable pageable) {
        if (!ALLOWED_PENDING_FOR.contains(pendingFor)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unsupported 'pendingFor', supported commands are: " + ALLOWED_PARAM);
        }
        if (context.hasPermission(pendingFor + "_ALL_PAYMENT")) {
            enableFilter(pendingFor);
            return service.findPendingFor(pendingFor, pageable).map(requestMapper);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User has no permission to " + pendingFor + " payment request");
    }

    private void enableFilter(CommandType commandType) {
        switch (commandType) {
            case SUBMIT:
                filterConfig.enableChildFilter(PaymentEntryStatus.OPEN);
                break;
            case VERIFY:
                filterConfig.enableChildFilter(PaymentEntryStatus.SUBMITTED);
                break;
            case CONFIRM:
                filterConfig.enableChildFilter(PaymentEntryStatus.VERIFIED);
                break;
            case REVIEW:
                filterConfig.enableChildFilter(PaymentEntryStatus.CONFIRMED);
                break;
            case APPROVE:
                filterConfig.enableChildFilter(PaymentEntryStatus.REVIEWED);
                break;
            case CASH_OUT:
                filterConfig.enableChildFilter(PaymentEntryStatus.APPROVED);
        }
    }

}