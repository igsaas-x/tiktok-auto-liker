package com.construction.feature.contract.service;

import com.construction.feature.contract.domain.PaymentStep;
import com.construction.feature.contract.repository.ContractRepository;
import com.construction.feature.contract.repository.PaymentStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentStepService {

    private final PaymentStepRepository repository;
    private final ContractRepository contractRepository;

    public PaymentStep cashOut(PaymentStep paymentStep) {
        paymentStep.setPaid(true);
        // update contract payment
        var contract = paymentStep.getContract();
        contract.addPaidAmount(paymentStep.getAmount());
        contract.addPaidStep();
        contractRepository.save(contract);
        return repository.save(paymentStep);
    }
}
