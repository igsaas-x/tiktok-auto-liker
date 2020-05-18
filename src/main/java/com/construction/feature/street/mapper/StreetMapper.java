package com.construction.feature.street.mapper;

import com.construction.feature.street.domain.Street;
import com.construction.feature.street.dto.StreetDTO;
import com.construction.persistence.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StreetMapper extends EntityMapper<StreetDTO, Street> {
}