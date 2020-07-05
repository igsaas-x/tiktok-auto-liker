package com.construction.feature.task.domain;

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
@Table(name = "task_assign", uniqueConstraints = @UniqueConstraint(name = "task_user", columnNames = {"task_id", "app_user_id"}))
@Accessors(chain = true)
@Where(clause = "status <> 'DELETED'")
@SQLDelete(sql = "update task_assign set status = 'DELETED' where id = ? and version = ?")
public class TaskAssign extends AssignEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    private Task task;
}
