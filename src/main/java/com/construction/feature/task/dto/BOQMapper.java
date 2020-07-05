package com.construction.feature.task.dto;

import com.construction.feature.house.service.HouseService;
import com.construction.feature.project.services.ProjectService;
import com.construction.feature.street.service.StreetService;
import com.construction.feature.task.domain.BOQ;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class BOQMapper implements DtoMapper<BOQDto, BOQ> {

    private final ProjectService projectService;
    private final StreetService streetService;
    private final HouseService houseService;

    public BOQMapper(ProjectService projectService, StreetService streetService, HouseService houseService) {
        this.projectService = projectService;
        this.streetService = streetService;
        this.houseService = houseService;
    }

    @Override
    public BOQ toEntity(BOQDto boqDto) {
        var boq = new BOQ();
        if (boqDto.getHouseId() != null) {
            boq.setHouse(houseService.getById(boqDto.getHouseId()));
        }
        if (boqDto.getProjectId() != null) {
            boq.setProject(projectService.getById(boqDto.getProjectId()));
        }
        if (boqDto.getStreetId() != null) {
            boq.setStreet(streetService.getById(boqDto.getStreetId()));
        }
        boq.setCode(boqDto.getCode());
        boq.setDetails(boqDto.getDetails());
        return boq;
    }

    @Override
    public BOQDto toDto(BOQ entity) {
        var dto = new BOQDto()
                .setCreatedBy(entity.getCreatedBy() == null ? null : entity.getCreatedBy().getUserName())
                .setUpdatedBy(entity.getUpdatedBy() == null ? null : entity.getUpdatedBy().getUserName())
                .setCode(entity.getCode())
                .setCreatedAt(entity.getCreatedAt())
                .setUpdatedAt(entity.getUpdatedAt())
                .setStatus(entity.getStatus())
                .setDetails(entity.getDetails());
        if (entity.getProject() != null) {
            dto.setProjectId(entity.getProject().getId());
        }
        if (entity.getStreet() != null) {
            dto.setStreetId(entity.getStreet().getId());
        }
        if (entity.getHouse() != null) {
            dto.setHouseId(entity.getHouse().getId());
        }
        return dto;
    }
}
