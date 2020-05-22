package com.construction.feature.street.dto;

import com.construction.feature.project.services.ProjectService;
import com.construction.feature.street.domain.Street;
import com.construction.persistence.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreetMapper implements DtoMapper<StreetDto, Street> {

    @Autowired
    private ProjectService projectService;

    @Override
    public Street toEntity(StreetDto streetDto) {
        var project = streetDto.getProjectId() == null ? null : projectService.getById(streetDto.getProjectId());
        return new Street()
                .setName(streetDto.getName())
                .setProject(project)
                .setDescription(streetDto.getDescription());
    }
}
