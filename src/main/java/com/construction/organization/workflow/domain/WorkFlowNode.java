package com.construction.organization.workflow.domain;

import com.construction.persistence.domain.SimpleAuditingEntity;
import com.construction.user.authorization.domain.UserRole;
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
public class WorkFlowNode extends SimpleAuditingEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private WorkFlow workFlow;

    @Enumerated(EnumType.STRING)
    private NodeType type;

    @NotNull
    private String name; // can be: verify, approve, review...

    @OneToOne // one parent > one child
    @JoinColumn(name = "parent_id" , updatable = false)
    private WorkFlowNode parent; // parent node

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private UserRole role;

    private boolean first = false;

    private boolean last = true;

    private String description;
}
