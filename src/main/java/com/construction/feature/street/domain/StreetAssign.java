package com.construction.feature.street.domain;

import com.construction.persistence.domain.AssignEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Where(clause = "status <> 'DELETED'")
@SQLDelete(sql = "update street_assign set status = 'DELETED' where id = ? and version =?")
@Table(uniqueConstraints = @UniqueConstraint(name = "street_user", columnNames = {"street_id", "app_user_id"}))
public class StreetAssign extends AssignEntity {

    @ManyToOne
    @JoinColumn
    private Street street;
}
