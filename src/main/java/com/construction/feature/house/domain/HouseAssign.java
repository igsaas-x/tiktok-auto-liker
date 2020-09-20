package com.construction.feature.house.domain;

import com.construction.persistence.domain.AssignEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(uniqueConstraints = @UniqueConstraint(name = "house_user", columnNames = {"house_id", "app_user_id"}))
public class HouseAssign extends AssignEntity {

    @ManyToOne
    @JoinColumn
    private House house;
}
