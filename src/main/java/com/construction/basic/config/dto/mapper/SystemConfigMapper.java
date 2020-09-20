package com.construction.basic.config.dto.mapper;

import com.construction.basic.config.domain.SystemConfig;
import com.construction.basic.config.dto.SystemConfigDTO;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SystemConfigMapper extends DtoMapper<SystemConfig, SystemConfigDTO> {

    protected SystemConfigMapper() {
        super(SystemConfig.class, SystemConfigDTO.class);
    }

    @Override
    public SystemConfig toEntity(SystemConfigDTO dto) {
        SystemConfig entity = new SystemConfig();
        entity.setCode(dto.getCode());
        entity.setValue(dto.getValue());
        return entity;
    }

    @Override
    public SystemConfigDTO apply(SystemConfig entity) {
        SystemConfigDTO dto = new SystemConfigDTO();
        dto.setCode(entity.getCode());
        dto.setValue(entity.getValue());
        return dto;
    }

    public List<SystemConfig> toEntityList(List<SystemConfigDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<SystemConfigDTO> toDtoList(List<SystemConfig> entityList) {
        return entityList.stream().map(this).collect(Collectors.toList());
    }
}