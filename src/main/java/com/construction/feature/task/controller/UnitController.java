package com.construction.feature.task.controller;

import com.construction.feature.task.domain.Unit;
import com.construction.feature.task.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
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
    public Map<String, Object> delete(@PathVariable("id") final Long id) {
        repository.deleteById(id);
        return Map.of("success", true);
    }
}
