package com.construction.feature.task.domain;

import com.construction.persistence.domain.AuditingEntity;
import com.construction.persistence.domain.ObjectStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Filter(name = "readWriteFilter",
        condition = "created_by = :id or " +
                "exists (select 1 from task_assign ta where ta.task_id = id and ta.app_user_id = :id and ta.assign_for = :assignFor)")
@Filter(name = "readFilter",
        condition = "created_by = :id or exists(select 1 from task_assign ta where ta.task_id = id and ta.app_user_id = :id)")
public class Task extends AuditingEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(updatable = false)
    private BOQ boq;

    @ManyToOne
    @JoinColumn(updatable = false)
    private TaskTemplate taskTemplate;

    @Column(nullable = false)
    private Integer quantity;

    private String unit;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(columnDefinition = "DECIMAL default 0", nullable = false)
    private BigDecimal paidAmount;

    @Column(nullable = false)
    private BigDecimal availableAmount;

    @Column(columnDefinition = "DECIMAL default 100")
    private Float availableAmountAsPercent;

    @PrePersist
    private void prePersist() {
        if (taskTemplate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "task template cannot be null");
        }
        if (!taskTemplate.isLeaf()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "template is not leaf");
        }
        if (getStatus() == null) {
            setStatus(ObjectStatus.OPEN);
        }
        if (totalPrice == null) {
            totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
        if (paidAmount == null) {
            paidAmount = BigDecimal.ZERO;
        }
        if (availableAmount == null) {
            availableAmount = totalPrice;
        }
        if (availableAmountAsPercent == null) {
            availableAmountAsPercent = 100F;
        }
    }

    @PreUpdate
    private void preUpdate() {
        if (paidAmount != null && paidAmount.compareTo(totalPrice) >= 0) {
            setStatus(ObjectStatus.CLOSED);
        }
        if (availableAmount != null && availableAmount.equals(BigDecimal.ZERO)) {
            setStatus(ObjectStatus.CLOSED);
        }
    }

    public void reduceAvailableAmountAsPercent(Float percent) {
        if (this.availableAmountAsPercent != null && this.availableAmountAsPercent > 0F) {
            this.availableAmountAsPercent = availableAmountAsPercent - percent;
            if (this.availableAmountAsPercent < 0F) {
                this.availableAmountAsPercent = 0F;
            }
        }
    }
}
