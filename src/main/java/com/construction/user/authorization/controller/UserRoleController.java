package com.construction.user.authorization.controller;

import com.construction.user.authorization.dto.UserRoleDTO;
import com.construction.user.authorization.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/user-role")
@RestController
@Api(tags = "UserRole API")
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody UserRoleDTO userRole) {
        userRoleService.save(userRole);
    }

    @ApiOperation("Delete based on primary key")
    @GetMapping("/{id}")
    public UserRoleDTO findById(@PathVariable("id") Long id) {
        Optional<UserRoleDTO> dtoOptional = userRoleService.findById(id);
        return dtoOptional.orElse(null);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userRoleService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<UserRoleDTO> list() {
        return userRoleService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<UserRoleDTO> pageQuery(Pageable pageable) {
        return userRoleService.findAll(pageable);
    }

/*    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public UserRoleDTO update(@RequestBody UserRoleDTO dto) {
        return userRoleService.updateById(dto);
    }*/
}