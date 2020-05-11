package com.construction.feature.project.domain;

import com.construction.feature.project.listener.ProjectListener;
import com.construction.feature.status.ObjectStatus;
import com.construction.persistence.domain.AuditingEntity;
import com.construction.user.authentication.domain.AppUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(ProjectListener.class)
public class Project extends AuditingEntity {

    @Column(nullable = false)
    private String objectType;

    @Column(nullable = false)
    private String objectName;

    private String code;

    @Enumerated(EnumType.STRING)
    private ObjectStatus status = ObjectStatus.OPEN;

    @ManyToOne
    @JoinColumn
    private AppUser approvedBy;

    private LocalDateTime approveAt;

    @Column(columnDefinition = "mediumtext")
    private String description;
}
