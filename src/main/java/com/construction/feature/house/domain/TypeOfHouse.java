package com.construction.feature.house.domain;

import com.construction.persistence.domain.VersionEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class TypeOfHouse extends VersionEntity {

    @Column(nullable = false)
    private String name;

    private String details;
}
