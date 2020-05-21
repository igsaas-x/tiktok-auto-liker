package com.construction.feature.task.controller;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.service.BOQService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bOQ")
@RestController
@Api(tags = "BOQ API")
public class BOQController {

    @Autowired
    private BOQService service;

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody BOQ bOQ) {
        service.save(bOQ);
    }

    @GetMapping("/{id}")
    public BOQ findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<BOQ> list() {
        return service.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<BOQ> pageQuery(Pageable pageable) {
        return service.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public BOQ update(@PathVariable Long id, @RequestBody BOQ dto) {
        return service.updateById(id, dto);
    }
}