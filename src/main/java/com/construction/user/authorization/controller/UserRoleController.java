package com.construction.user.authorization.controller;

import com.construction.user.authorization.domain.UserRole;
import com.construction.user.authorization.dto.PermissionDto;
import com.construction.user.authorization.dto.RoleDto;
import com.construction.user.authorization.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/role")
@RestController
@Api(tags = "UserRole API")
@AllArgsConstructor
public class UserRoleController {

    private final UserRoleService service;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('ALL_ALL')")
    public UserRole save(@RequestBody RoleDto roleDto) {
        return service.save(roleDto);
    }

    @GetMapping("/{id}")
    public UserRole findById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ALL_ALL')")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<UserRole> list() {
        return service.getAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<UserRole> pageQuery(Pageable pageable) {
        return service.getAll(pageable);
    }

    @ApiOperation("Update one data")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ALL_ALL')")
    public UserRole update(@PathVariable Long id, @RequestBody RoleDto dto) {
        return service.updateById(id, dto);
    }

    @GetMapping("/{id}/permissions")
    public List<PermissionDto> getPermissionByRoleId(@PathVariable Long id) {
        return service.getRolePermissionDto(id);
    }
}