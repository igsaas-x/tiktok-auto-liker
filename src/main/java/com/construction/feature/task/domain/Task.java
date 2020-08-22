package com.construction.feature.task.domain;

import com.construction.persistence.domain.AuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Filter(name = "assignedObjectFilter",
        condition = "exists(select 1 from task_assign ta where ta.task_id = id and ta.app_user_id = :id)")
@Filter(name = "myObjectFilter",
        condition = "created_by = :id")
@Filter(name = "readableObjectFilter",
        condition = "created_by = :id or exists(select 1 from task_assign ta where ta.task_id = id and ta.app_user_id = :id)")
public class Task extends AuditingEntity {

    @JsonIgnore
    @ManyToOne
    private BOQ boq;

    @NotNull
    @ManyToOne
    @JoinColumn
    private TaskTemplate taskTemplate;

    private Integer quantity;

    private String unit;

    private BigDecimal unitPrice;

    @NotNull
    private BigDecimal totalPrice;

    private BigDecimal actualPrice;

    @PrePersist
    private void prePersist() {
        if (taskTemplate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "task template cannot be null");
        }
        if (!taskTemplate.isLeaf()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "template is not leaf");
        }
    }
}
