package com.construction.feature.house.domain;

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
@SQLDelete(sql = "update house_assign set status = 'DELETED' where id =? and version = ?")
public class HouseAssign extends AssignEntity {

    @ManyToOne
    @JoinColumn
    private House house;
}
