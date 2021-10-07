package com.construction.feature.contract.service;

import com.construction.feature.contract.domain.ContractPaymentRequest;
import com.construction.feature.contract.domain.ContractStatusHistory;
import com.construction.feature.contract.repository.ContractStatusHistoryRepository;
import com.construction.organization.payment.domain.CommandType;
import com.construction.user.authentication.domain.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractStatusHistoryService {

    private final ContractStatusHistoryRepository repository;

    @Async
    public void addHistory(ContractPaymentRequest request,
                           CommandType commandType,
                           String attachment,
                           AppUser doneBy,
                           BigDecimal newApprovedAmount,
                           String comment) {
        var history = new ContractStatusHistory()
                .setPaymentRequest(request)
                .setAttachment(attachment)
                .setCommandType(commandType)
                .setApprovedAmount(newApprovedAmount)
                .setComment(comment);
        history.setCreatedBy(doneBy);
        repository.save(history);
    }

    public List<ContractStatusHistory> getByPaymentRequestId(final Long id) {
        return repository.findAllByPaymentRequestId(id, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
