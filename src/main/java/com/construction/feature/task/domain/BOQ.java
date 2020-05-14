package com.construction.feature.task.domain;

import com.construction.feature.house.domain.House;
import com.construction.feature.project.domain.Project;
import com.construction.feature.street.domain.Street;
import com.construction.persistence.domain.ExtendedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class BOQ extends ExtendedEntity {

    private String code;

    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne
    @JoinColumn
    private House house;

    @ManyToOne
    @JoinColumn
    private Street street;
}
