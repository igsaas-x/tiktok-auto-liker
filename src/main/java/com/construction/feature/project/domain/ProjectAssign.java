package com.construction.feature.project.domain;

import com.construction.persistence.domain.AssignEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(name = "project_user", columnNames = {"project_id", "app_user_id"}))
@Accessors(chain = true)
public class ProjectAssign extends AssignEntity {

    @ManyToOne
    @JoinColumn
    private Project project;
}
