package com.construction.feature.house.controllers;

import com.construction.feature.house.domain.House;
import com.construction.feature.house.services.HouseService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/house")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    ResponseEntity<Object> search(House house, Pageable pageable) {
        return houseService.search(house, pageable);
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
