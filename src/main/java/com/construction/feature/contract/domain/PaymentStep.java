package com.construction.feature.contract.domain;

import com.construction.persistence.domain.VersionEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class PaymentStep extends VersionEntity {

    @Column(name = "contract_id")
    private Contract contract;

    @Column(nullable = false)
    private int sequenceNumber;

    @Column
    private float percentage;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column
    private String note;

    private boolean paid;
}
