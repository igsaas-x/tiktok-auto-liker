package com.construction.feature.task.mapper.impl;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.dto.BOQDTO;
import com.construction.feature.task.mapper.BOQMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BOQMapperImpl implements BOQMapper {
    @Override
    public BOQ toEntity(BOQDTO dto) {
        BOQ entity = new BOQ();
        entity.setCode(dto.getCode());
        entity.setProject(dto.getProject());
        entity.setHouse(dto.getHouse());
        entity.setStreet(dto.getStreet());
        return entity;
    }

    @Override
    public BOQDTO toDto(BOQ entity) {
        BOQDTO dto = new BOQDTO();
        dto.setCode(entity.getCode());
        dto.setProject(entity.getProject());
        dto.setHouse(entity.getHouse());
        dto.setStreet(entity.getStreet());
        return dto;
    }

    @Override
    public List<BOQ> toEntityList(List<BOQDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<BOQDTO> toDtoList(List<BOQ> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}