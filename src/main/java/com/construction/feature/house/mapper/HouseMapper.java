package com.construction.feature.house.mapper;

import com.construction.feature.house.domain.House;
import com.construction.feature.house.dto.HouseDTO;
import com.construction.persistence.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HouseMapper extends EntityMapper<HouseDTO, House> {
}