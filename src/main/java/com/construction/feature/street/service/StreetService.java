package com.construction.feature.street.service;

import com.construction.feature.street.domain.Street;
import com.construction.feature.street.repository.StreetRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StreetService {

    @Autowired
    private StreetRepository repository;
    @Autowired
    private EntityDataMapper dataMapper;

    public Street save(Street dto) {
        return repository.save(dto);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Street findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Street.class, id));
    }

    public List<Street> findAll() {
        return repository.findAll();
    }

    public Page<Street> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Street update(Long id, final Street street) {
        var target = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Street.class, id));
        target = dataMapper.mapObject(street, target, Street.class);
        return repository.save(target);
    }
}