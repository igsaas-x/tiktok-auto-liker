package com.construction.feature.street.controller;

import com.construction.feature.street.domain.Street;
import com.construction.feature.street.service.StreetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/street")
@RestController
@Api(tags = "Street API")
public class StreetController {

    @Autowired
    private StreetService service;

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public Street save(@RequestBody Street street) {
        return service.save(street);
    }

    @GetMapping("/{id}")
    public Street findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ApiOperation("Delete by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<Street> list() {
        return service.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<Street> pageQuery(Pageable pageable) {
        return service.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public Street update(@PathVariable Long id, @RequestBody Street dto) {
        return service.update(id, dto);
    }
}