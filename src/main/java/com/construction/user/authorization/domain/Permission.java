package com.construction.user.authorization.domain;

import com.construction.persistence.domain.VersionEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Permission extends VersionEntity {

    @Enumerated(EnumType.STRING)
    private ActionName actionName;

    private String entityName;

    @Column(unique = true)
    private String codeName;

    private String description;
}
