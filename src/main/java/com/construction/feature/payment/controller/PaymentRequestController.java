package com.construction.feature.payment.controller;

import com.construction.feature.payment.domain.PaymentRequest;
import com.construction.feature.payment.service.PaymentRequestService;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/payment-request")
@RestController
@Api(tags = "PaymentRequest API")
public class PaymentRequestController {

    @Autowired
    private PaymentRequestService service;
    @Autowired
    private FilterConfig filterConfig;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_PAYMENTREQUEST')")
    public void save(@RequestBody PaymentRequest paymentRequest) {
        service.save(paymentRequest);
    }

    @GetMapping("/{id}")
    public PaymentRequest findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "paymentRequest");
        return service.getById(id);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "paymentRequest");
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<PaymentRequest> list() {
        filterConfig.configureFilter(ActionName.READ, "paymentRequest");
        return service.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<PaymentRequest> pageQuery(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "paymentRequest");
        return service.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public PaymentRequest update(@PathVariable Long id, @RequestBody PaymentRequest dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "paymentRequest");
        return service.updateById(id, dto);
    }
}