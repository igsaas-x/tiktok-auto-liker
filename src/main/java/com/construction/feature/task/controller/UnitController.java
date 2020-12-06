package com.construction.feature.task.controller;

import com.construction.feature.task.domain.Unit;
import com.construction.feature.task.repository.UnitRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/unit")
public class UnitController {

    private final UnitRepository repository;

    @GetMapping
    public List<Unit> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Unit getOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Unit.class, id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ALL_ALL')")
    public Unit create(@RequestBody Unit unit) {
        if (unit.getId() != null) {
            repository.findById(unit.getId()).ifPresentOrElse(
                    oldUnit -> {
                        oldUnit.setName(unit.getName());
                        repository.save(oldUnit);
                    },
                    () -> repository.save(unit));
            return unit;
        } else {
            return repository.save(unit);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ALL_ALL')")
    public Map<String, Object> delete(@PathVariable("id") final Long id) {
        repository.deleteById(id);
        return Map.of("success", true);
    }
}
