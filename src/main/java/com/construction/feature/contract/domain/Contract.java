package com.construction.feature.contract.domain;

import com.construction.feature.task.domain.BOQ;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.domain.AuditingEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Filter(name = "myObjectFilter", condition = "created_by = :id")
@Filter(name = "readableObjectFilter", condition = "created_by = :id")
@FilterDef(name = "pendingRequestFilter", parameters = @ParamDef(name = "status", type = "string"))
public class Contract extends AuditingEntity {

    @JoinColumn(name = "sub_constructor_id")
    @ManyToOne
    SubConstructor subConstructor;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "boq_contract",
            uniqueConstraints = {@UniqueConstraint(columnNames = {"contract_id", "boq_id"})},
            joinColumns = {@JoinColumn(name = "contract_id")},
            inverseJoinColumns = {@JoinColumn(name = "boq_id")})
    List<BOQ> boqs;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    List<PaymentStep> paymentSteps;

    @Column(nullable = false)
    BigDecimal totalAmount;

    BigDecimal paidAmount;

    int totalStep;

    int paidStep;

    String note;

    public void addPaidAmount(BigDecimal amount) {
        if (paidAmount == null) {
            paidAmount = amount;
        } else {
            paidAmount = paidAmount.add(amount);
        }
    }

    public void addPaidStep() {
        paidStep++;
    }
}
