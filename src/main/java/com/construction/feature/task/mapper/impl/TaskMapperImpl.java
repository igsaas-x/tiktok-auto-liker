package com.construction.feature.task.mapper.impl;

import com.construction.feature.task.domain.Task;
import com.construction.feature.task.dto.TaskDTO;
import com.construction.feature.task.mapper.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task toEntity(TaskDTO dto) {
        Task entity = new Task();
        entity.setTypeOfWork(dto.getTypeOfWork());
        entity.setHouse(dto.getHouse());
        entity.setProject(dto.getProject());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setParent(dto.getParent());
        entity.setLeaf(dto.isLeaf());
        entity.setFloor(dto.getFloor());
        entity.setBoq(dto.getBoq());
        entity.setContractType(dto.getContractType());
        entity.setQuantity(dto.getQuantity());
        entity.setUnit(dto.getUnit());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setActualPrice(dto.getActualPrice());
        return entity;
    }

    @Override
    public TaskDTO toDto(Task entity) {
        TaskDTO dto = new TaskDTO();
        dto.setTypeOfWork(entity.getTypeOfWork());
        dto.setHouse(entity.getHouse());
        dto.setProject(entity.getProject());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setParent(entity.getParent());
        dto.setLeaf(entity.isLeaf());
        dto.setFloor(entity.getFloor());
        dto.setBoq(entity.getBoq());
        dto.setContractType(entity.getContractType());
        dto.setQuantity(entity.getQuantity());
        dto.setUnit(entity.getUnit());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setActualPrice(entity.getActualPrice());
        return dto;
    }

    @Override
    public List<Task> toEntityList(List<TaskDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> toDtoList(List<Task> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}