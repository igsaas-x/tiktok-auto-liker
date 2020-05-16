package com.construction.feature.payment.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import com.construction.feature.payment.domain.PaymentRequest;
import com.construction.feature.payment.services.PaymentRequestService;

@RestController
@CrossOrigin
@RequestMapping("/paymentRequest")
public class PaymentRequestController {

    private final PaymentRequestService paymentRequestService;

    public PaymentRequestController(PaymentRequestService paymentRequestService) {
        this.paymentRequestService = paymentRequestService;
    }

     @GetMapping
     ResponseEntity<Object> search(PaymentRequest paymentRequest, Pageable pageable) {
             return paymentRequestService.search(paymentRequest, pageable);
     }
     @PostMapping
     void create() {
     }
     @PutMapping
     void update(){
     }
     @DeleteMapping("/")
     void delete(){
     }
}
