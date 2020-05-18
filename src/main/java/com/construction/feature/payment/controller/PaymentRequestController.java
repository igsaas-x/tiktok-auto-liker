package com.construction.feature.payment.controller;

import com.construction.feature.payment.dto.PaymentRequestDTO;
import com.construction.feature.payment.service.PaymentRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/payment-request")
@RestController
@Api(tags = "PaymentRequest API")
public class PaymentRequestController {
    private final PaymentRequestService paymentRequestService;

    public PaymentRequestController(PaymentRequestService paymentRequestService) {
        this.paymentRequestService = paymentRequestService;
    }

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody PaymentRequestDTO paymentRequest) {
        paymentRequestService.save(paymentRequest);
    }

    @ApiOperation("Delete based on primary key")
    @GetMapping("/{id}")
    public PaymentRequestDTO findById(@PathVariable("id") Long id) {
        Optional<PaymentRequestDTO> dtoOptional = paymentRequestService.findById(id);
        return dtoOptional.orElse(null);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        paymentRequestService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<PaymentRequestDTO> list() {
        return paymentRequestService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<PaymentRequestDTO> pageQuery(Pageable pageable) {
        return paymentRequestService.findAll(pageable);
    }

/*    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public PaymentRequestDTO update(@RequestBody PaymentRequestDTO dto) {
        return paymentRequestService.updateById(dto);
    }*/
}