package com.construction.feature.address.domain;

import com.construction.persistence.domain.VersionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "location")
@Accessors(chain = true)
public class Address extends VersionEntity {

    private String nameEn;

    private String nameKh;

    private int locationTypeId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Address parent;
}
