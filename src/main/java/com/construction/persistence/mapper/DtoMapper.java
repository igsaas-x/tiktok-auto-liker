package com.construction.persistence.mapper;

import org.modelmapper.ModelMapper;

import java.util.function.Function;

public abstract class DtoMapper<Entity, Dto> implements Function<Entity, Dto> {

    protected final ModelMapper modelMapper;
    private final Class<Entity> entityClass;
    private final Class<Dto> dtoClass;

    protected DtoMapper(Class<Entity> entityClass, Class<Dto> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @Override
    public Dto apply(Entity entity) {
        return modelMapper.map(entity, dtoClass);
    }

    public Entity toEntity(Dto dto) {
        return modelMapper.map(dto, entityClass);
    }
}
