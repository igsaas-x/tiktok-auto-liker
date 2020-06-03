package com.construction.organization.subconstructor.domain;

import com.construction.persistence.domain.VersionEntity;
import com.construction.user.authorization.domain.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "constructor_node")
@Accessors(chain = true)
public class SubConstructorWorkFlowNode extends VersionEntity {

    private String name;

    @ManyToOne
    @JoinColumn
    private UserRole role; // role to accomplish
}
