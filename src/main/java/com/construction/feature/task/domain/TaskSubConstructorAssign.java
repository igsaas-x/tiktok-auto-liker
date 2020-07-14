package com.construction.feature.task.domain;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.persistence.domain.VersionEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "task_sub_constructor_assign", uniqueConstraints = @UniqueConstraint(
        name = "task_sub_constructor",
        columnNames = {"task_id", "sub_constructor_id"}))
@Accessors(chain = true)
public class TaskSubConstructorAssign extends VersionEntity {

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "sub_constructor_id")
    private SubConstructor subConstructor;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
