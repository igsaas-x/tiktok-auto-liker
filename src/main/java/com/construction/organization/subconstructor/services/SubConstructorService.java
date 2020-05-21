package com.construction.organization.subconstructor.services;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.repository.SubConstructorRepository;
import com.construction.persistence.service.EntityDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubConstructorService {

    @Autowired
    private SubConstructorRepository repository;
    @Autowired
    private EntityDataMapper dataMapper;

    public SubConstructor create(final SubConstructor subConstructor) {
        return repository.save(subConstructor);
    }

    public SubConstructor getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<SubConstructor> getAll() {
        return repository.findAll();
    }

    public Page<SubConstructor> getAllAsPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void delete(Long id) {
        var target = repository.findById(id).orElseThrow();
        repository.delete(target);
    }

    public SubConstructor update(Long id, SubConstructor source) {
        var target = repository.findById(id).orElseThrow();
        target = dataMapper.mapObject(source, target, SubConstructor.class);
        return repository.save(target);
    }

}
