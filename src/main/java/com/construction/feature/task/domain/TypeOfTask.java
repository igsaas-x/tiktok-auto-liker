package com.construction.feature.task.domain;

import com.construction.persistence.domain.VersionEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Filter(name = "adminFilter", condition = "1 = 0")
public class TypeOfTask extends VersionEntity {

    @Column(unique = true)
    private String code;

    @NotNull
    private String name;
}
