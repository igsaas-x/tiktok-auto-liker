package com.construction.persistence.mapper;

public interface DtoEntityMapper<Input, Output> {
    Output toEntity(Input input);
    Input toDto(Output output);
}
