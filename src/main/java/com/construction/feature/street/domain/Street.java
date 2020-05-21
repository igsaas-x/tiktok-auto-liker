package com.construction.feature.street.domain;

import com.construction.feature.project.domain.Project;
import com.construction.persistence.domain.AuditingEntity;
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
@Filter(name = "assignedObjectFilter",
        condition = "project_id in (SELECT pa.project_id FROM project_assign pa WHERE pa.app_user_id = :id)")
@Filter(name = "myObjectFilter",
        condition = "created_by = :id or project_id = (select p.id from project p where p.created_by = :id)")
public class Street extends AuditingEntity {

    private String name;

    @ManyToOne
    @JoinColumn
    private Project project;

    private String description;
}
