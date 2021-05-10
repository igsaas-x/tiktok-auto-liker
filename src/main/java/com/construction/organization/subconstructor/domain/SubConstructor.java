package com.construction.organization.subconstructor.domain;

import com.construction.persistence.converter.StringSetConverter;
import com.construction.persistence.domain.AuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "sub_constructor")
@Accessors(chain = true)
@EntityListeners(SubConstructorListener.class)
@Filter(name = "myObjectFilter", condition = "created_by = :id")
@Filter(name = "readableObjectFilter", condition = "created_by = :id")
public class SubConstructor extends AuditingEntity {

    @Column(unique = true)
    private String externalId;

    private String engFullName;

    private String khFullName;

    private String firstName;

    private String lastName;

    private String image;

    @Convert(converter = StringSetConverter.class)
    private Set<String> attachment;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private IDType idType;

    private String idNumber;

    private String mobile;

    @Embedded
    private Address address;

    @Column(columnDefinition = "mediumtext")
    private String base64;
}
