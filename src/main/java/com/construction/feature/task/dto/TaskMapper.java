package com.construction.feature.task.dto;

import com.construction.feature.house.service.HouseService;
import com.construction.feature.project.services.ProjectService;
import com.construction.feature.street.service.StreetService;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.repository.BOQRepository;
import com.construction.feature.task.repository.TaskRepository;
import com.construction.persistence.mapper.DtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskMapper implements DtoMapper<TaskDto, Task> {

    private final ProjectService projectService;
    private final StreetService streetService;
    private final HouseService houseService;
    private final BOQRepository boqRepository;
    private final TaskRepository repository;

    @Override
    public Task toEntity(TaskDto taskDto) {
        var task = new Task()
                .setActualPrice(taskDto.getActualPrice())
                .setBelongTo(taskDto.getBelongTo())
                .setCode(taskDto.getCode())
                .setContractType(taskDto.getContractType())
                .setDescription(taskDto.getDescription())
                .setLeaf(taskDto.isLeaf())
                .setName(taskDto.getName())
                .setTotalPrice(taskDto.getTotalPrice())
                .setUnit(taskDto.getUnit())
                .setTypeOfWork(taskDto.getTypeOfWork())
                .setContractType(taskDto.getContractType())
                .setFloor(taskDto.getFloor())
                .setQuantity(taskDto.getQuantity());
        if (taskDto.getProjectId() != null) {
            task.setProject(projectService.getById(taskDto.getProjectId()));
        }
        if (taskDto.getStreetId() != null) {
            task.setStreet(streetService.getById(taskDto.getStreetId()));
        }
        if (taskDto.getHouseId() != null) {
            task.setHouse(houseService.getById(taskDto.getHouseId()));
        }
        if (taskDto.getBoqId() != null) {
            task.setBoq(boqRepository.findById(taskDto.getBoqId()).orElseThrow());
        }
        if (taskDto.getParentId() != null) {
            task.setParent(repository.findById(taskDto.getParentId()).orElseThrow());
        }
        return task;
    }

    @Override
    public TaskDto toDto(Task entity) {
        var task = new TaskDto()
                .setId(entity.getId())
                .setActualPrice(entity.getActualPrice())
                .setBelongTo(entity.getBelongTo())
                .setCode(entity.getCode())
                .setContractType(entity.getContractType())
                .setDescription(entity.getDescription())
                .setName(entity.getName())
                .setLeaf(entity.isLeaf())
                .setTotalPrice(entity.getTotalPrice())
                .setUnit(entity.getUnit())
                .setUnitPrice(entity.getUnitPrice())
                .setFloor(entity.getFloor())
                .setTypeOfWork(entity.getTypeOfWork())
                .setQuantity(entity.getQuantity())
                .setCreatedAt(entity.getCreatedAt())
                .setUpdatedAt(entity.getUpdatedAt())
                .setVerifiedAt(entity.getVerifiedAt())
                .setApprovedAt(entity.getApprovedAt())
                .setCreatedBy(entity.getCreatedBy() == null ? null : entity.getCreatedBy().getUserName())
                .setUpdatedBy(entity.getUpdatedBy() == null ? null : entity.getUpdatedBy().getUserName())
                .setVerifiedBy(entity.getVerifiedBy() == null ? null : entity.getVerifiedBy().getUserName())
                .setApprovedBy(entity.getApprovedBy() == null ? null : entity.getApprovedBy().getUserName());
        if (entity.getHouse() != null) {
            task.setHouseId(entity.getHouse().getId());
            task.setHouseNo(entity.getHouse().getHouseNo());
        }
        if (entity.getParent() != null) {
            task.setParentId(entity.getParent().getId());
        }
        if (entity.getProject() != null) {
            task.setProjectId(entity.getProject().getId());
            task.setProjectObjectName(entity.getProject().getObjectName());
        }
        if (entity.getStreet() != null) {
            task.setStreetId(entity.getStreet().getId());
            task.setStreetName(entity.getStreet().getName());
        }
        if (entity.getBoq() != null) {
            task.setBoqId(entity.getBoq().getId());
            task.setBoqCode(entity.getBoq().getCode());
        }
        return task;
    }
}
