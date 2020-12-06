package com.construction.feature.house.domain;

import com.construction.feature.project.domain.Project;
import com.construction.feature.street.domain.Street;
import com.construction.persistence.domain.AuditingEntity;
import com.construction.persistence.domain.ObjectStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Filter(name = "readWriteFilter",
        condition = "created_by = :id or " +
                "exists (SELECT 1 FROM house_assign ha WHERE ha.app_user_id = :id and ha.house_id = id and ha.assign_for = :assignFor)")
@Filter(name = "readFilter",
        condition = "created_by = :id or exists (SELECT 1 FROM house_assign ha WHERE ha.app_user_id = :id and ha.house_id = id)")
public class House extends AuditingEntity {

    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne
    @JoinColumn
    private Street street;

    @ManyToOne
    @JoinColumn
    private TypeOfHouse typeOfHouse;

    private String houseNo;

    private Float houseWidth;

    private Float houseLong;

    private Float landWidth;

    private Float landLong;

    @PrePersist
    private void prePersist() {
        if (street != null) {
            if (!ObjectStatus.APPROVED.equals(street.getStatus())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "street is not APPROVED");
            }
        }
        if (project != null) {
            if (!ObjectStatus.APPROVED.equals(project.getStatus())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "project is not APPROVED");
            }
        }
    }
}
