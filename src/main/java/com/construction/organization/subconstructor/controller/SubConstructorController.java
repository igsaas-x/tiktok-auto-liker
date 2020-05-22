package com.construction.organization.subconstructor.controller;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.services.SubConstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subconstructor")
public class SubConstructorController {

    @Autowired
    private SubConstructorService service;

    @GetMapping("/page")
    public Page<SubConstructor> getAll(Pageable pageable) {
        return service.getAllAsPage(pageable);
    }

    @GetMapping
    public List<SubConstructor> getAll() {
        return service.getAll();
    }

    @PostMapping
    public SubConstructor create(@RequestBody SubConstructor subConstructor) {
        return service.create(subConstructor);
    }

    @PutMapping("/{id}")
    public SubConstructor update(@PathVariable Long id, @RequestBody SubConstructor subConstructor) {
        return service.update(id, subConstructor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_SUBCONSTRUCTOR')")
    public SubConstructor verify(@PathVariable Long id) {
        return service.verify(id);
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_SUBCONSTRUCTOR')")
    public SubConstructor approve(@PathVariable Long id) {
        return service.approve(id);
    }
}
