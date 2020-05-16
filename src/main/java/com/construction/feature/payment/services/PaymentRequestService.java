package com.construction.feature.payment.services;

import com.construction.persistence.utils.SFWhere;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired ; 
import com.construction.feature.payment.repositories.PaymentRequestRepository; 
import com.construction.feature.payment.domain.PaymentRequest;

@Service
public class PaymentRequestService {
    @Autowired
    PaymentRequestRepository paymentRequestRepository;

    public ResponseEntity<Object> search(PaymentRequest paymentRequest, Pageable pageable) {
        Page<PaymentRequest> all = paymentRequestRepository.findAll(SFWhere.and(paymentRequest)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
