package com.construction.persistence.utils;

public interface DtoMapper<I, O> {
    O toEntity(I i);
}
