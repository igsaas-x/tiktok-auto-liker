package com.construction.basic.config.dto.mapper;

import com.construction.basic.config.domain.SystemConfig;
import com.construction.basic.config.dto.SystemConfigDTO;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SystemConfigMapper extends DtoMapper<SystemConfig, SystemConfigDTO> {

    public List<SystemConfig> toEntityList(List<SystemConfigDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<SystemConfigDTO> toDtoList(List<SystemConfig> entityList) {
        return entityList.stream().map(this).collect(Collectors.toList());
    }
}