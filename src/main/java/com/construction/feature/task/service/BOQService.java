package com.construction.feature.task.service;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.repository.BOQRepository;
import com.construction.persistence.service.EntityDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BOQService {

    @Autowired
    private BOQRepository repository;
    @Autowired
    private EntityDataMapper dataMapper;

    public BOQ save(BOQ dto) {
        return repository.save(dto);
    }

    public void save(List<BOQ> dtos) {
        repository.saveAll(dtos);
    }

    public void deleteById(Long id) {
        var boq = repository.findById(id).orElseThrow();
        repository.delete(boq);
    }

    public BOQ findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<BOQ> findAll() {
        return repository.findAll();
    }

    public Page<BOQ> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public BOQ updateById(Long id, BOQ boq) {
        var target = repository.findById(id).orElseThrow();
        target = dataMapper.mapObject(boq, target, BOQ.class);
        return repository.save(target);
    }
}