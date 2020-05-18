package com.construction.feature.house.mapper.impl;

import com.construction.feature.house.domain.House;
import com.construction.feature.house.dto.HouseDTO;
import com.construction.feature.house.mapper.HouseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HouseMapperImpl implements HouseMapper {
    @Override
    public House toEntity(HouseDTO dto) {
        House entity = new House();
        entity.setProject(dto.getProject());
        entity.setTypeOfHouse(dto.getTypeOfHouse());
        entity.setStreet(dto.getStreet());
        entity.setHouseNo(dto.getHouseNo());
        entity.setHouseWidth(dto.getHouseWidth());
        entity.setHouseLong(dto.getHouseLong());
        entity.setLandWidth(dto.getLandWidth());
        entity.setLandLong(dto.getLandLong());
        return entity;
    }

    @Override
    public HouseDTO toDto(House entity) {
        HouseDTO dto = new HouseDTO();
        dto.setProject(entity.getProject());
        dto.setTypeOfHouse(entity.getTypeOfHouse());
        dto.setStreet(entity.getStreet());
        dto.setHouseNo(entity.getHouseNo());
        dto.setHouseWidth(entity.getHouseWidth());
        dto.setHouseLong(entity.getHouseLong());
        dto.setLandWidth(entity.getLandWidth());
        dto.setLandLong(entity.getLandLong());
        return dto;
    }

    @Override
    public List<House> toEntityList(List<HouseDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<HouseDTO> toDtoList(List<House> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}