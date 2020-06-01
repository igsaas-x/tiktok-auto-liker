package com.construction.feature.task.dto;

import com.construction.feature.house.service.HouseService;
import com.construction.feature.project.services.ProjectService;
import com.construction.feature.street.service.StreetService;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.repository.BOQRepository;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements DtoMapper<TaskDto, Task> {

    private final ProjectService projectService;
    private final StreetService streetService;
    private final HouseService houseService;
    private final BOQRepository boqRepository;

    public TaskMapper(ProjectService projectService,
                      StreetService streetService,
                      HouseService houseService,
                      BOQRepository boqRepository) {
        this.projectService = projectService;
        this.streetService = streetService;
        this.houseService = houseService;
        this.boqRepository = boqRepository;
    }

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
                .setActualPrice(taskDto.getActualPrice())
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
        return task;
    }
}
