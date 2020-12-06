package com.construction.feature.task.domain;

import com.construction.persistence.domain.AuditingEntity;
import com.construction.persistence.domain.SimpleAuditingEntity;
import com.construction.persistence.domain.VersionEntity;
import com.construction.persistence.exception.ValidationErrorException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "task_template")
@Accessors(chain = true)
public class TaskTemplate extends AuditingEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private TaskTemplate parent;

    private boolean firstLevel;

    private String code;

    private String name;

    private String description;

    private boolean leaf;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<TaskTemplate> child;

    @PrePersist
    private void validate() {
        if (parent == null) {
            firstLevel = true;
            return;
        }
        if (parent.isLeaf()) {
            throw new ValidationErrorException(this.getClass(), "parent", "parent is leaf");
        }
    }
}
