package com.construction.basic.keyvalue.controllers;

import com.construction.basic.keyvalue.domain.KeyValue;
import com.construction.basic.keyvalue.services.KeyValueService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/keyValue")
public class KeyValueController {

    private final KeyValueService keyValueService;

    public KeyValueController(KeyValueService keyValueService) {
        this.keyValueService = keyValueService;
    }

    @GetMapping
    ResponseEntity<Object> search(KeyValue keyValue, Pageable pageable) {
        return keyValueService.search(keyValue, pageable);
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
