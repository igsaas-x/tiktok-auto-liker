package com.construction.organization.subconstructor.controllers;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.services.SubConstructorService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/subConstructor")
public class SubConstructorController {

    private final SubConstructorService subConstructorService;

    public SubConstructorController(SubConstructorService subConstructorService) {
        this.subConstructorService = subConstructorService;
    }

    @GetMapping
    ResponseEntity<Object> search(SubConstructor subConstructor, Pageable pageable) {
        return subConstructorService.search(subConstructor, pageable);
    }

    @PostMapping
    void create() {
    }

    @PutMapping
    void update() {
    }

    @DeleteMapping("/")
    void delete() {
    }
}
