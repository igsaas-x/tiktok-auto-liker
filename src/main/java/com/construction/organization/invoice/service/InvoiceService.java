package com.construction.organization.invoice.service;

import com.construction.organization.invoice.domain.Invoice;
import com.construction.organization.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repository;

    public Invoice saveInvoice(final Map<String, Object> data) {
        return new Invoice();
    }
}
