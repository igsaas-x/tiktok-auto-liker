package com.construction.feature.project.domain;

import com.construction.persistence.domain.AuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Project extends AuditingEntity {

    @Column(nullable = false)
    private String objectType;

    @Column(nullable = false)
    private String objectName;

    private String code;

    @Column(columnDefinition = "mediumtext")
    private String description;
}
