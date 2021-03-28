package com.construction.organization.subconstructor.data;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.repository.SubConstructorRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubConstructorMapper extends DtoMapper<SubConstructor, SubConstructorDto> {

    @Autowired
    private SubConstructorRepository repository;

    @Override
    public SubConstructorDto apply(SubConstructor subConstructor) {
        return super.apply(subConstructor);
    }

    @Override
    public SubConstructor toEntity(SubConstructorDto subConstructorDto) {
        return repository.findById(subConstructorDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(SubConstructor.class, subConstructorDto.getId()));
    }
}
