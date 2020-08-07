package com.construction.feature.task.domain;

import com.construction.feature.house.domain.House;
import com.construction.feature.project.domain.Project;
import com.construction.feature.street.domain.Street;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.domain.AuditingEntity;
import com.construction.persistence.exception.ValidationErrorException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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

    @ManyToOne
    @JoinColumn
    private TypeOfTask typeOfTask;

    @Enumerated(EnumType.STRING)
    private TaskBelongTo belongTo;

    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne
    @JoinColumn
    private Street street;

    @ManyToOne
    @JoinColumn
    private House house;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Task parent;

    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "parent_id")
    private List<Task> child;

    private boolean firstLevel;

    private String code;

    private String name;

    private String description;

    private boolean leaf;

    private String floor;

    @ManyToOne
    @JoinColumn
    private BOQ boq;

    private String contractType;

    private Integer quantity;

    private String unit;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private BigDecimal actualPrice;

    @PrePersist
    private void validate() {
        if (parent == null) {
            firstLevel = true;
            return;
        }
        if (parent.isLeaf()) {
            throw new ValidationErrorException(this.getClass(), "parent", "parent is leaf");
        }
    }
}
