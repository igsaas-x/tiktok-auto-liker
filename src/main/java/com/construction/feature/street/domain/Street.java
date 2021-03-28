package com.construction.feature.street.domain;

import com.construction.persistence.domain.AuditingEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Filter(name = "readWriteFilter",
        condition = "created_by = :id or " +
                "exists (SELECT 1 FROM street_assign sa WHERE sa.app_user_id = :id and sa.street_id = id and sa.assign_for = :assignFor)")
@Filter(name = "readFilter",
        condition = "created_by = :id or exists (SELECT 1 FROM street_assign sa WHERE sa.app_user_id = :id and sa.street_id = id)")
public class Street extends AuditingEntity {

    @Column(unique = true)
    private String code;

    private String name;

    private String description;
}
