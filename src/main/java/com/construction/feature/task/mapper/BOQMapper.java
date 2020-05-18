package com.construction.feature.task.mapper;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.dto.BOQDTO;
import com.construction.persistence.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BOQMapper extends EntityMapper<BOQDTO, BOQ> {
}