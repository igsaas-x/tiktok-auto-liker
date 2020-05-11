package com.construction.feature.task.domain;

import com.construction.feature.house.domain.House;
import com.construction.feature.status.ObjectStatus;
import com.construction.persistence.domain.AuditingEntity;
import com.construction.user.authentication.domain.AppUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Task extends AuditingEntity {

    private String typeOfWork;

    @JoinColumn
    @ManyToOne
    private House house;

    private String code;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Task parent;

    private boolean leaf;

    private String floor;

    private String boq;

    private String contractType;

    private Integer quantity;

    private String unit;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private BigDecimal actualPrice;

    @Enumerated(EnumType.STRING)
    private ObjectStatus status = ObjectStatus.OPEN;

    @ManyToOne
    @JoinColumn
    private AppUser approvedBy;

    private LocalDateTime approvedAt;
}
