package com.construction.feature.task.mapper;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.dto.TaskDTO;
import com.construction.persistence.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
}