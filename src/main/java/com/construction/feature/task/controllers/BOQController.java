package com.construction.feature.task.controllers;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.services.BOQService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/boq")
public class BOQController {

    private final BOQService bOQService;

    public BOQController(BOQService bOQService) {
        this.bOQService = bOQService;
    }

    @GetMapping
    ResponseEntity<Object> search(BOQ bOQ, Pageable pageable) {
        return bOQService.search(bOQ, pageable);
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
