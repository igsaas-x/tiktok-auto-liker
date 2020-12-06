package com.construction.feature.task.domain;

import com.construction.feature.house.domain.House;
import com.construction.feature.project.domain.Project;
import com.construction.feature.street.domain.Street;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.domain.SimpleAuditingEntity;
import com.construction.persistence.domain.SimpleObjectStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "boq")
@Accessors(chain = true)
@Filter(name = "readFilter", condition = "created_by = :id or " +
        "exists (select t.id from task t, task_assign ta where t.id = ta.task_id and t.boq_id = id and ta.app_user_id = :id)")
@Filter(name = "readWriteFilter", condition = "created_by = :id")
public class BOQ extends SimpleAuditingEntity {

    @Column(unique = true)
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

    private String details;

    @Enumerated(EnumType.STRING)
    private SimpleObjectStatus status = SimpleObjectStatus.ACTIVE;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "boq_id")
    private List<Task> tasks;

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
        if (house != null) {
            if (!ObjectStatus.APPROVED.equals(house.getStatus())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "house is not APPROVED");
            }
        }
    }
}
