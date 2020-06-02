package com.construction.feature.street.domain;

import com.construction.persistence.domain.AuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Filter(name = "assignedObjectFilter",
        condition = "id in (SELECT sa.street_id FROM street_assign sa WHERE sa.app_user_id = :id)")
@Filter(name = "myObjectFilter",
        condition = "created_by = :id")
@Filter(name = "readableObjectFilter",
        condition = "created_by = :id or id in (SELECT sa.street_id FROM street_assign sa WHERE sa.app_user_id = :id)")
public class Street extends AuditingEntity {

    private String code;

    private String name;

    private String description;
}
