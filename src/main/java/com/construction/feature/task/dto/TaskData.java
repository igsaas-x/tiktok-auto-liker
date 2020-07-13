package com.construction.feature.task.dto;

import com.construction.persistence.dto.EntityData;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "task")
@Immutable
public class TaskData extends EntityData {
    private String name;
    @OneToMany
    @JoinColumn(name = "parent_id")
    private List<TaskData> child;
}
