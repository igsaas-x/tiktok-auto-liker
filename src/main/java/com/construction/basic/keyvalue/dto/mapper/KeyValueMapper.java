package com.construction.basic.keyvalue.dto.mapper;

import com.construction.basic.keyvalue.domain.KeyValue;
import com.construction.basic.keyvalue.dto.KeyValueDto;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KeyValueMapper extends DtoMapper<KeyValue, KeyValueDto> {

    protected KeyValueMapper() {
        super(KeyValue.class, KeyValueDto.class);
    }

    public List<KeyValue> toEntityList(List<KeyValueDto> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<KeyValueDto> toDtoList(List<KeyValue> entityList) {
        return entityList.stream().map(this).collect(Collectors.toList());
    }
}