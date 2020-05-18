package com.construction.basic.config.mapper;

import com.construction.basic.config.domain.SystemConfig;
import com.construction.basic.config.dto.SystemConfigDTO;
import com.construction.persistence.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SystemConfigMapper extends EntityMapper<SystemConfigDTO, SystemConfig> {
}