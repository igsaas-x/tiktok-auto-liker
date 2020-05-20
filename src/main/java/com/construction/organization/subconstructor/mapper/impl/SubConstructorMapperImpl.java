package com.construction.organization.subconstructor.mapper.impl;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.dto.SubConstructorDTO;
import com.construction.organization.subconstructor.mapper.SubConstructorMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubConstructorMapperImpl implements SubConstructorMapper {
    @Override
    public SubConstructor toEntity(SubConstructorDTO dto) {
        SubConstructor entity = new SubConstructor();
        entity.setCustomerId(dto.getCustomerId());
        entity.setEngFullName(dto.getEngFullName());
        entity.setKhFullName(dto.getKhFullName());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setGender(dto.getGender());
        entity.setIdType(dto.getIdType());
        entity.setIdNumber(dto.getIdNumber());
        entity.setMobile(dto.getMobile());
        entity.setAddress(dto.getAddress());
        return entity;
    }

    @Override
    public SubConstructorDTO toDto(SubConstructor entity) {
        SubConstructorDTO dto = new SubConstructorDTO();
        dto.setCustomerId(entity.getCustomerId());
        dto.setEngFullName(entity.getEngFullName());
        dto.setKhFullName(entity.getKhFullName());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setGender(entity.getGender());
        dto.setIdType(entity.getIdType());
        dto.setIdNumber(entity.getIdNumber());
        dto.setMobile(entity.getMobile());
        dto.setAddress(entity.getAddress());
        return dto;
    }

    @Override
    public List<SubConstructor> toEntityList(List<SubConstructorDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<SubConstructorDTO> toDtoList(List<SubConstructor> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}