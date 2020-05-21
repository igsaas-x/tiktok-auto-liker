package com.construction.organization.subconstructor.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {

    private String province;

    private String district;

    private String commune;

    private String details;
}
