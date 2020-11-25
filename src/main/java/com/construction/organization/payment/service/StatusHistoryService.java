package com.construction.organization.payment.service;

import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentEntry;
import com.construction.organization.payment.domain.StatusHistory;
import com.construction.organization.payment.repository.StatusHistoryRepository;
import com.construction.user.authentication.domain.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusHistoryService {

    private final StatusHistoryRepository repository;

    @Async
    public void addHistory(PaymentEntry entry, CommandType commandType, String attachment, AppUser doneBy, String comment) {
        var history = new StatusHistory()
                .setPaymentEntry(entry)
                .setCreatedBy(doneBy)
                .setAttachment(attachment)
                .setCommandType(commandType)
                .setComment(comment);
        repository.save(history);
    }

    public List<StatusHistory> getByPaymentEntryId(final Long id) {
        return repository.findAllByPaymentEntryId(id, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
