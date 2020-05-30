package com.construction.feature.street.domain;

import com.construction.persistence.domain.AssignEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@SQLDelete(sql = "update street_assign set status = 'DELETED' where id = ? and version =?")
public class StreetAssign extends AssignEntity {

    @ManyToOne
    @JoinColumn
    private Street street;
}
