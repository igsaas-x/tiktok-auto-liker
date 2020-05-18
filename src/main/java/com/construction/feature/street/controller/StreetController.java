package com.construction.feature.street.controller;

import com.construction.feature.street.dto.StreetDTO;
import com.construction.feature.street.service.StreetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/street")
@RestController
@Api(tags = "Street API")
public class StreetController {

    private final StreetService streetService;

    public StreetController(StreetService streetService) {
        this.streetService = streetService;
    }

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody StreetDTO street) {
        streetService.save(street);
    }

    @ApiOperation("Delete based on primary key")
    @GetMapping("/{id}")
    public StreetDTO findById(@PathVariable("id") Long id) {
        Optional<StreetDTO> dtoOptional = streetService.findById(id);
        return dtoOptional.orElse(null);
    }

    @ApiOperation("Delete by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        streetService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<StreetDTO> list() {
        return streetService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<StreetDTO> pageQuery(Pageable pageable) {
        return streetService.findAll(pageable);
    }

/*    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public StreetDTO update(@RequestBody StreetDTO dto) {
        return streetService.updateById(dto);
    }*/
}