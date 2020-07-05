package com.construction.feature.house.controller;

import com.construction.feature.FilterType;
import com.construction.feature.house.domain.House;
import com.construction.feature.house.domain.HouseAssign;
import com.construction.feature.house.dto.HouseDto;
import com.construction.feature.house.dto.HouseMapper;
import com.construction.feature.house.service.HouseService;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/house")
@RestController
@Api(tags = "House API")
public class HouseController {

    private final FilterConfig filterConfig;
    private final HouseService houseService;
    private final HouseMapper mapper;

    public HouseController(FilterConfig filterConfig, HouseService houseService, HouseMapper mapper) {
        this.filterConfig = filterConfig;
        this.houseService = houseService;
        this.mapper = mapper;
    }

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_HOUSE')")
    public House save(@RequestBody HouseDto dto) {
        var house = mapper.toEntity(dto);
        return houseService.save(house);
    }

    @GetMapping("/{id}")
    public House findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.getById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(House house, Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.search(house, pageable);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "house");
        houseService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<HouseDto> list() {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/assigned")
    public List<HouseDto> getAssignedHouse() {
        return houseService.getAssigned().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/pending/verify")
    public List<HouseDto> getPendingForVerify(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.getPendingForVerify().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/pending/approve")
    public List<HouseDto> getPendingForApprove(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.getPendingForApprove().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<House> pageQuery(Pageable pageable, @RequestParam FilterType filter) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.getAll(pageable, filter);
    }

    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public House update(@PathVariable Long id, @RequestBody HouseDto dto) {
        var house = mapper.toEntity(dto);
        filterConfig.configureFilter(ActionName.UPDATE, "house");
        return houseService.updateById(id, house);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_HOUSE') or hasAuthority('VERIFY_ASSIGNED_HOUSE')")
    public House verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        return houseService.verify(id);
    }

    @PostMapping("/{id}/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_HOUSE') or hasAuthority('ASSIGN_ASSIGNED_HOUSE')")
    public HouseAssign assign(@PathVariable Long id, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        return houseService.assign(id, userId, assignFor);
    }

    @PostMapping("/{id}/unassign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_HOUSE') or hasAuthority('ASSIGN_ASSIGNED_HOUSE')")
    public void unAssign(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        houseService.unAssign(id, userId);
    }

    @PostMapping("/batch/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_HOUSE') or hasAuthority('VERIFY_ASSIGNED_HOUSE')")
    public List<HouseDto> verifyAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        return houseService.verifyAll(ids.getIds()).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_HOUSE') or hasAuthority('APPROVE_ASSIGNED_HOUSE')")
    public House approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "house");
        return houseService.approve(id);
    }

    @PostMapping("/batch/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_HOUSE') or hasAuthority('APPROVE_ASSIGNED_HOUSE')")
    public List<HouseDto> approveAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.APPROVE, "house");
        return houseService.approveAll(ids.getIds()).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}