package com.construction.organization.invoice.repository;

import com.construction.organization.invoice.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
