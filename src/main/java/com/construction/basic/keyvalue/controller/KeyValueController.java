package com.construction.basic.keyvalue.controller;

import com.construction.basic.keyvalue.dto.KeyValueDto;
import com.construction.basic.keyvalue.service.KeyValueService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/key-value")
@RestController
@RequiredArgsConstructor
public class KeyValueController {

    private final KeyValueService keyValueService;

    @ApiOperation("Add new data")
    @PostMapping
    public void save(@RequestBody KeyValueDto keyValue) {
        keyValueService.save(keyValue);
    }

    @GetMapping("/{id}")
    public KeyValueDto findById(@PathVariable("id") Long id) {
        return keyValueService.findById(id).orElse(null);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        keyValueService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<KeyValueDto> list() {
        return keyValueService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<KeyValueDto> pageQuery(Pageable pageable) {
        return keyValueService.findAll(pageable);
    }
}