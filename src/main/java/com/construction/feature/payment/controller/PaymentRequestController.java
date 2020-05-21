package com.construction.feature.payment.controller;

import com.construction.feature.payment.domain.PaymentRequest;
import com.construction.feature.payment.service.PaymentRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/payment-request")
@RestController
@Api(tags = "PaymentRequest API")
public class PaymentRequestController {

    private PaymentRequestService service;

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody PaymentRequest paymentRequest) {
        service.save(paymentRequest);
    }

    @GetMapping("/{id}")
    public PaymentRequest findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<PaymentRequest> list() {
        return service.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<PaymentRequest> pageQuery(Pageable pageable) {
        return service.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public PaymentRequest update(@PathVariable Long id, @RequestBody PaymentRequest dto) {
        return service.updateById(id, dto);
    }
}