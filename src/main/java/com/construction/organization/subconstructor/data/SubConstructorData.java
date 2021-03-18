package com.construction.organization.subconstructor.data;

import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Immutable
@Table(name = "sub_constructor")
public class SubConstructorData {
    @Id
    @Column
    private Long id;
    @Column
    private String base64;
}
