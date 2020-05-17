package com.construction.feature.project.domain;

import com.construction.persistence.domain.AuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class ProjectAudit extends AuditingEntity {

    private Long projectId;

    @Column(nullable = false)
    private String objectType;

    @Column(nullable = false)
    private String objectName;

    @Column(unique = true)
    private String code;

    @Column(columnDefinition = "mediumtext")
    private String description;
}
