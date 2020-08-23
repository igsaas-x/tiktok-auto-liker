package com.construction.organization.payment.controller;

import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.StatusHistory;
import com.construction.organization.payment.dto.PaymentEntryDto;
import com.construction.organization.payment.dto.mapper.PaymentEntryMapper;
import com.construction.organization.payment.service.PaymentEntryService;
import com.construction.organization.payment.service.StatusHistoryService;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/payment-entry")
@RequiredArgsConstructor
public class PaymentEntryController {

    static final List<CommandType> ALLOWED_PENDING_FOR = Arrays.asList(CommandType.values());
    static final String ALLOWED_PARAM = "SUBMIT/VERIFY/CONFIRM/REVIEW/APPROVE/CASH-OUT/REJECT";

    private final FilterConfig filterConfig;
    private final PaymentEntryService service;
    private final PaymentEntryMapper mapper;
    private final StatusHistoryService historyService;

    @GetMapping("/{id}")
    public PaymentEntryDto getById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "payment");
        return mapper.apply(service.getById(id));
    }

    @ApiOperation("Submit command to payment request. Parameters are:" + ALLOWED_PARAM)
    @PutMapping("/command")
    public Map<String, Object> handleCommand(@RequestBody IdList ids, @RequestParam CommandType command, @RequestParam(required = false) String comment) {
        if (!ALLOWED_PENDING_FOR.contains(command)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unsupported 'command', supported commands are: " + ALLOWED_PARAM);
        }
        service.getByAllId(ids.getIds()).forEach(entry -> {
            service.handleCommand(entry, command, comment);
        });
        return Map.of("success", true);
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    public PaymentEntry update(@PathVariable Long id, @RequestBody PaymentEntryDto dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "payment");
        return service.update(id, mapper.toEntity(dto));
    }

    @ApiOperation("delete by Id")
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "payment");
        final var result = service.delete(id);
        return Map.of("success", result);
    }

    @GetMapping("/{id}/history")
    public List<StatusHistory> getPaymentRequestHistory(@PathVariable Long id) {
        return historyService.getByPaymentEntryId(id);
    }
}
