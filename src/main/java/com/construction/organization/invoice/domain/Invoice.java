package com.construction.organization.invoice.domain;

import com.construction.persistence.converter.JsonObjectConverter;
import com.construction.persistence.domain.VersionEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Invoice extends VersionEntity {

    @Column(nullable = false)
    private String invoiceNumber;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(columnDefinition = "mediumtext", nullable = false)
    @Convert(converter = JsonObjectConverter.class)
    private Map<String, Object> data;
}
