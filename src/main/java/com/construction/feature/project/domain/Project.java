package com.construction.feature.project.domain;

import com.construction.persistence.domain.AuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FilterDef(name = "assignedObjectFilter",
        defaultCondition = "id in (SELECT pa.project_id FROM project_assign pa WHERE pa.app_user_id = :id) ",
        parameters = @ParamDef(name = "id", type = "long"))
@FilterDef(name = "myObjectFilter",
        defaultCondition = "created_by = :id",
        parameters = @ParamDef(name = "id", type = "long"))
@Filter(name = "assignedObjectFilter")
@Filter(name = "myObjectFilter")
public class Project extends AuditingEntity {

    @Column(nullable = false)
    private String objectType;

    @Column(nullable = false)
    private String objectName;

    @Column(unique = true)
    private String code;

    @Column(columnDefinition = "mediumtext")
    private String description;
}
