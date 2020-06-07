package com.construction.organization.payment.controller;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.organization.payment.domain.PaymentCommand;
import com.construction.organization.payment.domain.PaymentRequest;
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

import java.util.Arrays;
import java.util.List;

@RequestMapping("/payment-request")
@RestController
@Api(tags = "PaymentRequest API")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentRequestController {

    static final List<PaymentCommand> ALLOWED_PENDING_FOR = Arrays.asList(PaymentCommand.values());
    static final String ALLOWED_PARAM = "submit/verify/confirm/review/approve/cash-out";

    final PaymentRequestService service;
    final PaymentRequestDtoMapper mapper;
    final ApplicationSecurityContext context;
    final FilterConfig filterConfig;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_PAYMENT')")
    public void save(@RequestBody PaymentRequestDto dto) {
        service.save(mapper.toEntity(dto));
    }

    @GetMapping("/{id}")
    public PaymentRequest findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "payment");
        return service.getById(id);
    }

    @ApiOperation("delete by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE,"payment");
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<PaymentRequest> list() {
        filterConfig.configureFilter(ActionName.READ, "payment");
        return service.findAll();
    }

    @ApiOperation("Find data pending. Parameters are:" + ALLOWED_PARAM)
    @GetMapping("/pending")
    public List<PaymentRequest> listPending(@RequestParam PaymentCommand pendingFor) {
        if (!ALLOWED_PENDING_FOR.contains(pendingFor)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unsupported 'pendingFor', supported commands are: " + ALLOWED_PARAM);
        }
        if (context.hasPermission(pendingFor + "_ALL_PAYMENT")) {
            return service.findPendingFor(pendingFor);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User has no permission to " + pendingFor + " payment request");
    }

    @ApiOperation("Submit command to payment request. Parameters are:" + ALLOWED_PARAM)
    @PutMapping("/{id}/command")
    public PaymentRequest handleCommand(@PathVariable Long id, @RequestParam PaymentCommand command) {
        if (!ALLOWED_PENDING_FOR.contains(command)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unsupported 'command', supported commands are: " + ALLOWED_PARAM);
        }
        var pendingRequests = listPending(command);
        var request = pendingRequests.stream()
                .filter(paymentRequest -> id.equals(paymentRequest.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment is not in state for: " + command));
        return service.handleCommand(request, command, context.authenticatedUser());
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<PaymentRequest> pageQuery(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ,"payment");
        return service.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    public PaymentRequest update(@PathVariable Long id, @RequestBody PaymentRequestDto dto) {
        filterConfig.configureFilter(ActionName.UPDATE,"payment");
        return service.updateById(id, mapper.toEntity(dto));
    }
}