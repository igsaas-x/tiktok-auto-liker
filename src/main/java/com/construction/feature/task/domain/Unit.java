package com.construction.feature.task.domain;

import com.construction.persistence.domain.VersionEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Unit extends VersionEntity {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
}
