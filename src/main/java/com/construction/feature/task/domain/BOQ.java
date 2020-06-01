package com.construction.feature.task.domain;

import com.construction.feature.house.domain.House;
import com.construction.feature.project.domain.Project;
import com.construction.feature.street.domain.Street;
import com.construction.persistence.domain.SimpleAuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Filter(name = "readableObjectFilter",
        condition = "created_by = :id or " +
                "exists (select t.id from task t, task_assign ta where t.id = ta.task_id and t.boq_id = id and ta.app_user_id = :id)")
@Filter(name = "myObjectFilter", condition = "created_by = :id")
public class BOQ extends SimpleAuditingEntity {

    private String code;

    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne
    @JoinColumn
    private House house;

    @ManyToOne
    @JoinColumn
    private Street street;

    private String details;
}
