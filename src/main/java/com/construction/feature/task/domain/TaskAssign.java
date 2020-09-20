package com.construction.feature.task.domain;

import com.construction.persistence.domain.AssignEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "task_assign", uniqueConstraints = @UniqueConstraint(name = "task_user", columnNames = {"task_id", "app_user_id"}))
@Accessors(chain = true)
public class TaskAssign extends AssignEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    private Task task;
}
