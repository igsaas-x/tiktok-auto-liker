package com.construction.persistence.mapper;

public interface DtoMapper<I, O> {
    O toEntity(I i);
}
