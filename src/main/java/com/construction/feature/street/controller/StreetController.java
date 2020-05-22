package com.construction.feature.street.controller;

import com.construction.feature.street.domain.Street;
import com.construction.feature.street.service.StreetService;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/street")
@RestController
@Api(tags = "Street API")
public class StreetController {

    @Autowired
    private StreetService service;
    @Autowired
    private FilterConfig filterConfig;

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

    @ApiOperation("Delete by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "street");
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<Street> list() {
        filterConfig.configureFilter(ActionName.READ, "street");
        return service.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<Street> pageQuery(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "street");
        return service.findAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public Street update(@PathVariable Long id, @RequestBody Street dto) {
        filterConfig.configureFilter(ActionName.UPDATE, "street");
        return service.update(id, dto);
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_ALL_STREET') or hasAuthority('VERIFY_ASSIGNED_STREET')")
    public Street verify(@PathVariable Long id){
        filterConfig.configureFilter(ActionName.VERIFY, "street");
        return service.verify(id);
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_ALL_STREET') or hasAuthority('APPROVE_ASSIGNED_STREET')")
    public Street approve(@PathVariable Long id){
        filterConfig.configureFilter(ActionName.APPROVE, "street");
        return service.approve(id);
    }
}