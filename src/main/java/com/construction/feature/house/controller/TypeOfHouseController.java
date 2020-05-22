package com.construction.feature.house.controller;

import com.construction.feature.house.domain.TypeOfHouse;
import com.construction.feature.house.repository.TypeOfHouseRepository;
import com.construction.persistence.service.EntityDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/house-type")
public class TypeOfHouseController {

    @Autowired
    private TypeOfHouseRepository repository;
    @Autowired
    private EntityDataMapper dataMapper;

    @GetMapping
    public List<TypeOfHouse> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public TypeOfHouse getOneById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_TYPEOFHOUSE')")
    public TypeOfHouse create(@RequestBody TypeOfHouse typeOfHouse) {
        return repository.save(typeOfHouse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ALL_TYPEOFHOUSE')")
    public TypeOfHouse update(@PathVariable Long id, @RequestBody TypeOfHouse typeOfHouse) {
        var target = repository.findById(id).orElseThrow();
        target = dataMapper.mapObject(typeOfHouse, target, TypeOfHouse.class);
        return repository.save(target);
    }

    @DeleteMapping("/id")
    @PreAuthorize("hasAuthority('DELETE_ALL_TYPEOFHOUSE')")
    public void deleteById(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
