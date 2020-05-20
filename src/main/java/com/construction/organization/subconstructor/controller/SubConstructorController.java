package com.construction.organization.subconstructor.controller;

import com.construction.organization.subconstructor.dto.SubConstructorDTO;
import com.construction.organization.subconstructor.service.SubConstructorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/sub-constructor")
@RestController
@Api(tags = "SubConstructor API")
public class SubConstructorController {
    private final SubConstructorService subConstructorService;

    public SubConstructorController(SubConstructorService subConstructorService) {
        this.subConstructorService = subConstructorService;
    }

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody SubConstructorDTO subConstructor) {
        subConstructorService.save(subConstructor);
    }

    @ApiOperation("Delete based on primary key")
    @GetMapping("/{id}")
    public SubConstructorDTO findById(@PathVariable("id") String id) {
        Optional<SubConstructorDTO> dtoOptional = subConstructorService.findById(id);
        return dtoOptional.orElse(null);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        subConstructorService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<SubConstructorDTO> list() {
        return subConstructorService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<SubConstructorDTO> pageQuery(Pageable pageable) {
        return subConstructorService.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public SubConstructorDTO update(@RequestBody SubConstructorDTO dto) {
        return subConstructorService.updateById(dto);
    }
}