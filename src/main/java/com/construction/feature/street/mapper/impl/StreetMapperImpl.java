package com.construction.feature.street.mapper.impl;

import com.construction.feature.street.domain.Street;
import com.construction.feature.street.dto.StreetDTO;
import com.construction.feature.street.mapper.StreetMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StreetMapperImpl implements StreetMapper {
    @Override
    public Street toEntity(StreetDTO dto) {
        Street entity = new Street();
        entity.setName(dto.getName());
        entity.setProject(dto.getProject());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    @Override
    public StreetDTO toDto(Street entity) {
        StreetDTO dto = new StreetDTO();
        dto.setName(entity.getName());
        dto.setProject(entity.getProject());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    @Override
    public List<Street> toEntityList(List<StreetDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<StreetDTO> toDtoList(List<Street> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}