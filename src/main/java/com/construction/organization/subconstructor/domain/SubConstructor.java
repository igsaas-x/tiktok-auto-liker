package com.construction.organization.subconstructor.domain;

import com.construction.feature.address.domain.Address;
import com.construction.persistence.domain.AuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class SubConstructor extends AuditingEntity {

    @Column(unique = true)
    private String customerId;

    private String engFullName;

    private String khFullName;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private IDType idType;

    private String idNumber;

    private String mobile;

    @Embedded
    private Address address;
}
