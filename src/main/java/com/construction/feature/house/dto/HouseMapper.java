package com.construction.feature.house.dto;

import com.construction.feature.house.domain.House;
import com.construction.feature.house.repository.TypeOfHouseRepository;
import com.construction.feature.project.services.ProjectService;
import com.construction.feature.street.service.StreetService;
import com.construction.persistence.utils.DtoMapper;
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
}
