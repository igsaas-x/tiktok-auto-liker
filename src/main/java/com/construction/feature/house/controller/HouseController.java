package com.construction.feature.house.controller;

import com.construction.feature.house.dto.HouseDTO;
import com.construction.feature.house.service.HouseService;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/house")
@RestController
@Api(tags = "House API")
public class HouseController {

    private final FilterConfig filterConfig;
    private final HouseService houseService;

    public HouseController(FilterConfig filterConfig, HouseService houseService) {
        this.filterConfig = filterConfig;
        this.houseService = houseService;
    }

    @ApiOperation("Add new data")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('CREATE_ALL_HOUSE')")
    public HouseDTO save(@RequestBody HouseDTO house) {
        return houseService.save(house);
    }

    @GetMapping("/{id}")
    public HouseDTO findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.findById(id);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "house");
        houseService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<HouseDTO> list() {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<HouseDTO> pageQuery(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public HouseDTO update(@PathVariable Long id, @RequestBody HouseDTO dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "house");
        return houseService.updateById(id, dto);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_HOUSE') or hasAuthority('VERIFY_ASSIGNED_HOUSE')")
    public HouseDTO verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        return houseService.verify(id);
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_HOUSE') or hasAuthority('APPROVE_ASSIGNED_HOUSE')")
    public HouseDTO approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "house");
        return houseService.approve(id);
    }
}