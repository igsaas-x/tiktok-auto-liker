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
@FilterDef(name = "readWriteFilter",
        defaultCondition = "created_by = :id or " +
                "exists (SELECT 1 FROM project_assign pa WHERE pa.project_id = id and pa.app_user_id = :id and pa.assign_for = :assignFor)",
        parameters = {@ParamDef(name = "id", type = "long"), @ParamDef(name = "assignFor", type = "string")})
@FilterDef(name = "readFilter",
        defaultCondition = "created_by = :id or " +
                "id in (SELECT pa.project_id FROM project_assign pa WHERE pa.app_user_id = :id) or " +
                "exists (SELECT 1 FROM project_assign pa WHERE pa.project_id = id and pa.app_user_id = :id) ",
        parameters = @ParamDef(name = "id", type = "long"))
@Filter(name = "readFilter")
@Filter(name = "readWriteFilter")
public class Project extends AuditingEntity {

    private String objectType;

    @Column(nullable = false)
    private String objectName;

    @Column(unique = true)
    private String code;

    @Column(columnDefinition = "mediumtext")
    private String description;
}
