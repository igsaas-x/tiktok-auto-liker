package com.construction.feature.contract.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.contract.domain.ContractPaymentRequest;
import com.construction.feature.contract.repository.ContractPaymentRequestRepository;
import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentStatus;
import com.construction.persistence.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static com.construction.organization.payment.domain.PaymentStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractPaymentRequestService {

    private final ContractPaymentRequestRepository repository;
    private final PaymentStepService paymentStepService;
    private final ContractStatusHistoryService historyService;
    private final ApplicationSecurityContext context;

    public ContractPaymentRequest processCommand(Long id,
                                                 CommandType command,
                                                 final String attachment,
                                                 final BigDecimal approveAmount,
                                                 final String comment) {
        var paymentRequest = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ContractPaymentRequest.class, id));
        if (approveAmount != null) {
            paymentRequest.setApprovedAmount(approveAmount);
        }
        switch (command) {
            case SUBMIT:
                if (!PaymentStatus.OPEN.equals(paymentRequest.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payment request is not in OPEN state");
                }
                paymentRequest.setStatus(PaymentStatus.SUBMITTED);
                break;
            case VERIFY:
                if (!PaymentStatus.SUBMITTED.equals(paymentRequest.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payment request is not in SUBMITTED state");
                }
                paymentRequest.setStatus(PaymentStatus.VERIFIED);
                break;
            case CONFIRM:
                if (!PaymentStatus.VERIFIED.equals(paymentRequest.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payment request is not in VERIFIED state");
                }
                paymentRequest.setStatus(PaymentStatus.CONFIRMED);
                break;
            case REVIEW:
                if (!PaymentStatus.CONFIRMED.equals(paymentRequest.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payment request is not in CONFIRMED state");
                }
                paymentRequest.setStatus(PaymentStatus.REVIEWED);
                break;
            case APPROVE:
                if (!PaymentStatus.REVIEWED.equals(paymentRequest.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payment request is not in REVIEWED state");
                }
                paymentRequest.setStatus(PaymentStatus.APPROVED);
                break;
            case CASH_OUT:
                if (!PaymentStatus.APPROVED.equals(paymentRequest.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payment request is not in APPROVED state");
                }
                paymentStepService.cashOut(paymentRequest.getPaymentStep());
                paymentRequest.setStatus(PaymentStatus.PAID);
                break;
            case REJECT:
                if (OPEN.equals(paymentRequest.getStatus()) || PAID.equals(paymentRequest.getStatus())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payment request is in status that cannot be rejected");
                }
                paymentRequest.setStatus(REJECTED);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "command not found");

        }
        historyService.addHistory(
                paymentRequest,
                command,
                attachment,
                context.authenticatedUser(),
                approveAmount == null ? paymentRequest.getPaymentStep().getAmount() : approveAmount,
                comment);
        return repository.save(paymentRequest);
    }

}
