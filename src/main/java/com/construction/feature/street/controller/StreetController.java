package com.construction.feature.street.controller;

import com.construction.feature.FilterType;
import com.construction.feature.street.domain.Street;
import com.construction.feature.street.domain.StreetAssign;
import com.construction.feature.street.service.StreetAssignService;
import com.construction.feature.street.service.StreetService;
import com.construction.persistence.domain.AssignFor;
import com.construction.persistence.dto.AssignedDto;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/street")
@RestController
@Api(tags = "Street API")
@RequiredArgsConstructor
public class StreetController {

    private final StreetService service;
    private final FilterConfig filterConfig;
    private final StreetAssignService assignService;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_STREET')")
    public Street save(@RequestBody Street street) {
        return service.save(street);
    }

    @GetMapping("/{id}")
    public Street findById(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.READ, "street");
        return service.getById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(Street street, Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "street");
        return service.search(street, pageable);
    }

    @ApiOperation("Delete by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "street");
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<Street> list() {
        filterConfig.configureFilter(ActionName.READ, "street");
        return service.getAll();
    }

    @ApiOperation("Find all data")
    @GetMapping("/pending/verify")
    public Page<Street> getPendingVerify(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "street");
        return service.getPendingForVerify(pageable);
    }

    @ApiOperation("Find approve data")
    @GetMapping("/pending/approve")
    public Page<Street> getPendingApprove(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "street");
        return service.getPendingForApprove(pageable);
    }

    @GetMapping("/pending/all")
    public Page<Street> getAllPending(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "street");
        return service.getAllPending(pageable);
    }

    @GetMapping("/assigned")
    public List<Street> getAssignedStreet() {
        return service.getAssignedStreet();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<Street> pageQuery(Pageable pageable, @RequestParam FilterType filter) {
        filterConfig.configureFilter(ActionName.READ, "street");
        return service.getAll(pageable, filter);
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    public Street update(@PathVariable Long id, @RequestBody Street street) {
        filterConfig.configureFilter(ActionName.UPDATE, "street");
        return service.update(id, street);
    }

    @PostMapping("/{id}/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_STREET') or hasAuthority('ASSIGN_ASSIGNED_STREET')")
    public StreetAssign assign(@PathVariable Long id, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "street");
        return service.assign(id, userId, assignFor);
    }

    @PostMapping("/batch/assign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_STREET') or hasAuthority('ASSIGN_ASSIGNED_STREET')")
    public boolean assignAll(@RequestBody IdList ids, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "street");
        ids.getIds().forEach(id -> service.assign(id, userId, assignFor));
        return true;
    }

    @PostMapping("/{id}/unassign/{userId}")
    @PreAuthorize("hasAuthority('ASSIGN_ALL_STREET') or hasAuthority('ASSIGN_ASSIGNED_STREET')")
    public void unAssign(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.ASSIGN, "street");
        service.unAssign(id, userId);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_STREET') or hasAuthority('VERIFY_ASSIGNED_STREET')")
    public Street verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "street");
        return service.verify(id);
    }

    @PostMapping("/batch/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_STREET') or hasAuthority('VERIFY_ASSIGNED_STREET')")
    public List<Street> verifyAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.VERIFY, "street");
        return service.verifyAll(ids.getIds());
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_STREET') or hasAuthority('APPROVE_ASSIGNED_STREET')")
    public Street approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "street");
        return service.approve(id);
    }

    @PostMapping("/batch/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_STREET') or hasAuthority('APPROVE_ASSIGNED_STREET')")
    public List<Street> approveAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.APPROVE, "street");
        return service.approveAll(ids.getIds());
    }

    @GetMapping("/{id}/assigned/user")
    public List<AssignedDto> getAssignedUser(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.READ, "street");
        return assignService.getAssignedUser(id);
    }
}