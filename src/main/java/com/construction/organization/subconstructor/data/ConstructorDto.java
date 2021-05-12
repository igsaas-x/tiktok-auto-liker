package com.construction.organization.subconstructor.data;

import com.construction.organization.subconstructor.domain.Address;
import com.construction.organization.subconstructor.domain.Gender;
import com.construction.organization.subconstructor.domain.IDType;
import com.construction.persistence.domain.ObjectStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Embedded;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ConstructorDto {

    private Long id;

    private String externalId;

    private String engFullName;

    private String khFullName;

    private String firstName;

    private String lastName;

    private String image;

    private Set<String> attachment;

    private Gender gender;

    private IDType idType;

    private String idNumber;

    private String mobile;

    @Embedded
    private Address address;

    private ObjectStatus status;

}
