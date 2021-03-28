package com.construction.feature.task.domain;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.domain.SimpleAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class BOQAssign extends SimpleAuditingEntity {

    @ManyToOne
    @JoinColumn(name = "boq_id")
    private BOQ boq;

    @ManyToOne
    @JoinColumn(name = "sub_constructor_id")
    private SubConstructor subConstructor;
}
