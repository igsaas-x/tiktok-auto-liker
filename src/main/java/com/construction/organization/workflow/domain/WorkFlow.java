package com.construction.organization.workflow.domain;

import com.construction.persistence.domain.VersionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "work_flow", uniqueConstraints = @UniqueConstraint(name = "work_flow_unique_name", columnNames = "name"))
public class WorkFlow extends VersionEntity {

    @NotNull
    @Column(unique = true)
    private String name;

    private String description;

    @OneToOne
    @JoinColumn
    @JsonIgnore
    private WorkFlowNode startBy;

    @OneToOne
    @JoinColumn
    @JsonIgnore
    private WorkFlowNode endBy;
}
