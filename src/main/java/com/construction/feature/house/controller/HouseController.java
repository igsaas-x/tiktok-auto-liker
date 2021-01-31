package com.construction.feature.house.controller;

import com.construction.feature.FilterType;
import com.construction.feature.house.domain.House;
import com.construction.feature.house.domain.HouseAssign;
import com.construction.feature.house.dto.HouseDto;
import com.construction.feature.house.dto.HouseMapper;
import com.construction.feature.house.service.HouseAssignService;
import com.construction.feature.house.service.HouseService;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.dto.AssignedDto;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.dto.IdListUserListBatch;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/house")
@RestController
@Api(tags = "House API")
@RequiredArgsConstructor
public class HouseController {

    private final FilterConfig filterConfig;
    private final HouseService houseService;
    private final HouseAssignService houseAssignService;
    private final HouseMapper mapper;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_HOUSE')")
    public HouseDto save(@RequestBody HouseDto dto) {
        var house = mapper.toEntity(dto);
        return mapper.apply(houseService.save(house));
    }

    @GetMapping("/{id}")
    public HouseDto findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return mapper.apply(houseService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(House house, Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.search(house, pageable);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "house");
        houseService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<HouseDto> list() {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.getAll().stream().map(mapper).collect(Collectors.toList());
    }

    @GetMapping("/assigned")
    public List<HouseDto> getAssignedHouse() {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.getAssigned().stream().map(mapper).collect(Collectors.toList());
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<HouseDto> pageQuery(Pageable pageable, @RequestParam FilterType filter) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.getAll(pageable, filter).map(mapper);
    }

    @GetMapping("/pending")
    public Page<HouseDto> getAllPending(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseService.getAllPending(pageable).map(mapper);
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    public HouseDto update(@PathVariable Long id, @RequestBody HouseDto dto) {
        var house = mapper.toEntity(dto);
        filterConfig.configureFilter(ActionName.UPDATE, "house");
        return mapper.apply(houseService.updateById(id, house));
    }

    @PostMapping("/{id}/verify")
    public HouseDto verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        return mapper.apply(houseService.verify(id));
    }

    @PostMapping("/{id}/assign/{userId}")
    public HouseAssign assign(@PathVariable Long id, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        return houseService.assign(id, userId, assignFor);
    }

    @PostMapping("/batch/assign/{userId}")
    public boolean assignAll(@RequestBody IdList ids, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        ids.getIds().forEach(id -> houseService.assign(id, userId, assignFor));
        return true;
    }

    @PostMapping("/batch/assign")
    public boolean assignAllBatch(@RequestBody IdListUserListBatch body, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        body.getUserIds().forEach(userId -> {
            body.getIds().forEach(id -> {
                try {
                    houseService.assign(id, userId, assignFor);
                } catch (final Exception e) {
                    log.error(String.format("error assign house id %s to user id %s", id, userId));
                }
            });
        });
        return true;
    }

    @PostMapping("/{id}/unassign/{userId}")
    public void unAssign(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        houseService.unAssign(id, userId);
    }

    @PostMapping("/batch/verify")
    public List<HouseDto> verifyAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.VERIFY, "house");
        return houseService.verifyAll(ids.getIds()).stream().map(mapper).collect(Collectors.toList());
    }

    @PostMapping("/{id}/approve")
    public HouseDto approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "house");
        return mapper.apply(houseService.approve(id));
    }

    @PostMapping("/batch/approve")
    public List<HouseDto> approveAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.APPROVE, "house");
        return houseService.approveAll(ids.getIds()).stream().map(mapper).collect(Collectors.toList());
    }

    @GetMapping("/{id}/assigned/user")
    public List<AssignedDto> getAssignedUser(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.READ, "house");
        return houseAssignService.getAssignedUser(id);
    }
}