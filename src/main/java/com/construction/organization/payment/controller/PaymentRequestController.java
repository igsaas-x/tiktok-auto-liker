package com.construction.organization.payment.controller;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.domain.PaymentRequestStatus;
import com.construction.organization.payment.domain.StatusHistory;
import com.construction.organization.payment.dto.PaymentEntryMapper;
import com.construction.organization.payment.dto.PaymentRequestDto;
import com.construction.organization.payment.dto.PaymentRequestDtoMapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    final PaymentRequestDtoMapper mapper;
    final PaymentEntryMapper entryMapper;
    final ApplicationSecurityContext context;
    final FilterConfig filterConfig;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_PAYMENT')")
    @Transactional
    public PaymentRequest save(@RequestBody PaymentRequestDto dto) {
        final var request = new PaymentRequest();
        final var paymentRequest = service.save(request);
        final var entries = dto.getEntries().stream()
                .map(entry -> entryMapper.toEntity(entry, paymentRequest))
                .collect(Collectors.toList());
        paymentRequest.setEntries(entries);
        return service.save(paymentRequest);
    }

    @GetMapping("/{id}")
    public PaymentRequest findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "payment");
        return service.getById(id);
    }

    @GetMapping("/sub-constructor/{id}")
    public List<PaymentRequest> findBySubConstructorId(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "payment");
        return service.getBySubConstructorId(id);
    }

    @ApiOperation("delete by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "payment");
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<PaymentRequestDto> list() {
        filterConfig.configureFilter(ActionName.READ, "payment");
        return service.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @ApiOperation("Find data pending. Parameters are:" + ALLOWED_PARAM)
    @GetMapping("/pending")
    public List<PaymentRequestDto> listPending(@RequestParam CommandType pendingFor) {
        if (!ALLOWED_PENDING_FOR.contains(pendingFor)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unsupported 'pendingFor', supported commands are: " + ALLOWED_PARAM);
        }
        if (context.hasPermission(pendingFor + "_ALL_PAYMENT")) {
            return service.findPendingFor(pendingFor).stream().map(mapper::toDto).collect(Collectors.toList());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User has no permission to " + pendingFor + " payment request");
    }

    @ApiOperation("Find data pending wait user to deal with")
    @GetMapping("/pending/all")
    public Map<CommandType, List<PaymentRequest>> getAllPending() {
        return service.getAllPending();
    }

    @ApiOperation("Submit command to payment request. Parameters are:" + ALLOWED_PARAM)
    @PutMapping("/{id}/command")
    public PaymentRequest handleCommand(@PathVariable Long id, @RequestParam CommandType command, @RequestParam(required = false) String comment) {
        if (!ALLOWED_PENDING_FOR.contains(command)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unsupported 'command', supported commands are: " + ALLOWED_PARAM);
        }
        if (command.equals(CommandType.REJECT)) {
            var request = service.getById(id);
            if (request.getStatus().equals(PaymentRequestStatus.APPROVED)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "request is approved, cannot be rejected");
            }
            return service.handleCommand(request, command, context.authenticatedUser(), comment);
        }
        var pendingRequests = listPending(command);
        var request = pendingRequests.stream()
                .filter(paymentRequest -> id.equals(paymentRequest.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment is not in state for: " + command));
        return service.handleCommand(mapper.toEntity(request), command, context.authenticatedUser(), comment);
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<PaymentRequest> pageQuery(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "payment");
        return service.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    public PaymentRequest update(@PathVariable Long id, @RequestBody PaymentRequestDto dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "payment");
        return service.updateById(id, mapper.toEntity(dto));
    }

    @GetMapping("/{id}/history")
    public List<StatusHistory> getPaymentRequestHistory(@PathVariable Long id) {
        return service.getByPaymentId(id);
    }
}