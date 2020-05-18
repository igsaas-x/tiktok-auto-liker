package com.construction.feature.street.domain;

import com.construction.feature.project.domain.Project;
import com.construction.persistence.domain.AuditingEntity;
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
public class Street extends AuditingEntity {

    private String name;

    @ManyToOne
    @JoinColumn
    private Project project;

    private String description;
}
