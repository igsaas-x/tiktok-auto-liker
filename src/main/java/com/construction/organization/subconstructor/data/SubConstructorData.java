package com.construction.organization.subconstructor.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Immutable
@Table(name = "sub_constructor")
public class SubConstructorData {
    @Id
    private Long id;

    @JsonProperty("name")
    private String engFullName;

    private String base64;
}
