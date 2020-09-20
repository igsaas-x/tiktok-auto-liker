package com.construction.feature.street.domain;

import com.construction.persistence.domain.AssignEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(uniqueConstraints = @UniqueConstraint(name = "street_user", columnNames = {"street_id", "app_user_id"}))
public class StreetAssign extends AssignEntity {

    @ManyToOne
    @JoinColumn
    private Street street;
}
