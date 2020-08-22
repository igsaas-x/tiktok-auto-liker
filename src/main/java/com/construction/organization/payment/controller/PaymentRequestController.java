package com.construction.organization.payment.controller;

import com.construction.organization.payment.dto.PaymentEntryDto;
import com.construction.organization.payment.dto.PaymentRequestDto;
import com.construction.organization.payment.dto.mapper.PaymentEntryMapper;
import com.construction.organization.payment.dto.mapper.PaymentRequestMapper;
import com.construction.organization.payment.service.PaymentEntryService;
import com.construction.organization.payment.service.PaymentRequestService;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/payment-request")
@RestController
@Api(tags = "PaymentRequest API")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentRequestController {

    final PaymentRequestService service;
    final PaymentRequestMapper requestMapper;
    final PaymentEntryService entryService;
    final PaymentEntryMapper entryMapper;
    final FilterConfig filterConfig;

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

    @GetMapping("/{id}/entries")
    public List<PaymentEntryDto> findAllPaymentEntry(@PathVariable("id") Long id) {
        return entryService.getByPaymentRequestId(id).stream().map(entryMapper).collect(Collectors.toList());
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

}