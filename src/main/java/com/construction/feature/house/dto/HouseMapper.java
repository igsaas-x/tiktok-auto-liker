package com.construction.feature.house.dto;

import com.construction.feature.house.domain.House;
import com.construction.feature.house.repository.TypeOfHouseRepository;
import com.construction.feature.project.services.ProjectService;
import com.construction.feature.street.service.StreetService;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.stereotype.Component;

@Component
public class HouseMapper implements DtoMapper<HouseDto, House> {

    private final ProjectService projectService;
    private final StreetService streetService;
    private final TypeOfHouseRepository typeOfHouseRepository;

    public HouseMapper(ProjectService projectService,
                       StreetService streetService,
                       TypeOfHouseRepository typeOfHouseRepository) {
        this.projectService = projectService;
        this.streetService = streetService;
        this.typeOfHouseRepository = typeOfHouseRepository;
    }

    @Override
    public House toEntity(HouseDto dto) {
        var typeOfHouse = dto.getTypeOfHouseId() == null ? null : typeOfHouseRepository.findById(dto.getTypeOfHouseId()).orElse(null);
        var project = dto.getProjectId() == null ? null : projectService.getById(dto.getProjectId());
        var street = dto.getStreetId() == null ? null : streetService.getById(dto.getStreetId());
        return new House().setTypeOfHouse(typeOfHouse)
                .setHouseLong(dto.getHouseLong())
                .setHouseNo(dto.getHouseNo())
                .setHouseWidth(dto.getHouseWidth())
                .setLandLong(dto.getLandLong())
                .setLandWidth(dto.getLandWidth())
                .setProject(project)
                .setStreet(street);
    }

    @Override
    public HouseDto toDto(House entity) {
        return new HouseDto()
                .setId(entity.getId())
                .setCreatedBy(entity.getCreatedBy() == null ? null : entity.getCreatedBy().getUserName())
                .setUpdatedBy(entity.getUpdatedBy() == null ? null : entity.getUpdatedBy().getUserName())
                .setVerifiedBy(entity.getVerifiedBy() == null ? null : entity.getVerifiedBy().getUserName())
                .setApprovedBy(entity.getApprovedBy() == null ? null : entity.getApprovedBy().getUserName())
                .setTypeOfHouseId(entity.getTypeOfHouse() == null ? null : entity.getTypeOfHouse().getId())
                .setCreatedAt(entity.getCreatedAt())
                .setUpdatedAt(entity.getUpdatedAt())
                .setVerifiedAt(entity.getVerifiedAt())
                .setApprovedAt(entity.getApprovedAt())
                .setHouseLong(entity.getHouseLong())
                .setHouseWidth(entity.getHouseWidth())
                .setLandLong(entity.getLandLong())
                .setLandWidth(entity.getLandWidth())
                .setProjectId(entity.getProject() == null ? null : entity.getProject().getId())
                .setStreetId(entity.getStreet() == null ? null : entity.getStreet().getId());
    }
}
