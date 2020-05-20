package com.construction.feature.task.domain;

import com.construction.persistence.domain.AssignEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "boq_assign", uniqueConstraints = @UniqueConstraint(name = "boq_user", columnNames = {"boq_id", "app_user_id"}))
@Accessors(chain = true)
@SQLDelete(sql = "update boq_assign set status = 'DELETED' where id = ? and version = ?")
public class BOQAssign extends AssignEntity {

    @ManyToOne(optional = false)
    @JoinColumn
    private BOQ boq;
}
