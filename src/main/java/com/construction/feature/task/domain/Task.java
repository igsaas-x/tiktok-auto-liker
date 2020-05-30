package com.construction.feature.task.domain;

import com.construction.feature.house.domain.House;
import com.construction.feature.project.domain.Project;
import com.construction.feature.street.domain.Street;
import com.construction.persistence.domain.AuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Filter(name = "assignedObjectFilter",
        condition = "exists(select 1 from task_assign ta where ta.task_id = id and ta.app_user_id = :id)")
@Filter(name = "myObjectFilter")
public class Task extends AuditingEntity {

    private String typeOfWork;

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

    private String code;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Task parent;

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
}
