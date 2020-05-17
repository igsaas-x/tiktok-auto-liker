package com.construction.feature.project.domain;

import com.construction.persistence.domain.VersionEntity;
import com.construction.user.authentication.domain.AppUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(name = "project_user", columnNames = {"project_id", "app_user_id"}))
@Accessors(chain = true)
public class ProjectAssign extends VersionEntity {

    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne
    @JoinColumn
    private AppUser appUser;
}
