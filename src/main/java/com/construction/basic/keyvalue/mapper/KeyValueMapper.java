package com.construction.basic.keyvalue.mapper;

import com.construction.basic.keyvalue.domain.KeyValue;
import com.construction.basic.keyvalue.dto.KeyValueDTO;
import com.construction.persistence.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KeyValueMapper extends EntityMapper<KeyValueDTO, KeyValue> {
}