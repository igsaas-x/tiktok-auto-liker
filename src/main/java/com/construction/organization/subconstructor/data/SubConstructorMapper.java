package com.construction.organization.subconstructor.data;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.repository.SubConstructorRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubConstructorMapper {

    @Autowired
    private SubConstructorRepository repository;

    public SubConstructorData map(final SubConstructor subConstructor) {
        return new SubConstructorData()
                .setEngFullName(subConstructor.getEngFullName())
                .setId(subConstructor.getId());
    }

    public SubConstructor map(final SubConstructorData subConstructorData) {
        return repository.findById(subConstructorData.getId())
                .orElseThrow(() -> new ResourceNotFoundException(SubConstructor.class, subConstructorData.getId()));
    }

    public List<SubConstructorData> toDataList(final List<SubConstructor> subConstructors) {
        return subConstructors.stream().map(this::map).collect(Collectors.toList());
    }

    public List<SubConstructor> toEntityList(final List<SubConstructorData> subConstructorDataList) {
        return subConstructorDataList.stream().map(this::map).collect(Collectors.toList());
    }
}
