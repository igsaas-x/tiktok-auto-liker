package com.construction.feature.house.controller;

import com.construction.feature.house.dto.HouseDTO;
import com.construction.feature.house.service.HouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/house")
@RestController
@Api(tags = "House API")
public class HouseController {
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody HouseDTO house) {
        houseService.save(house);
    }

    @ApiOperation("Delete based on primary key")
    @GetMapping("/{id}")
    public HouseDTO findById(@PathVariable("id") Long id) {
        Optional<HouseDTO> dtoOptional = houseService.findById(id);
        return dtoOptional.orElse(null);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        houseService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<HouseDTO> list() {
        return houseService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<HouseDTO> pageQuery(Pageable pageable) {
        return houseService.findAll(pageable);
    }

/*    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public HouseDTO update(@RequestBody HouseDTO dto) {
        return houseService.updateById(dto);
    }*/
}