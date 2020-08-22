package com.construction.organization.payment.service;

import com.construction.organization.payment.domain.StatusHistory;
import com.construction.organization.payment.repository.StatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusHistoryService {

    private final StatusHistoryRepository repository;

    public List<StatusHistory> getByPaymentEntryId(final Long id) {
        return repository.findAllByPaymentEntryId(id);
    }
}
