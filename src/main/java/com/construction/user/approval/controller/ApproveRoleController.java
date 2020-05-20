package com.construction.user.approval.controller;

import com.construction.user.approval.dto.ApproveRoleDTO;
import com.construction.user.approval.service.ApproveRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/approve-role")
@RestController
@Api(tags = "ApproveRole API")
public class ApproveRoleController {
    private final ApproveRoleService approveRoleService;

    public ApproveRoleController(ApproveRoleService approveRoleService) {
        this.approveRoleService = approveRoleService;
    }

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody ApproveRoleDTO approveRole) {
        approveRoleService.save(approveRole);
    }

    @ApiOperation("Delete based on primary key")
    @GetMapping("/{id}")
    public ApproveRoleDTO findById(@PathVariable("id") Long id) {
        Optional<ApproveRoleDTO> dtoOptional = approveRoleService.findById(id);
        return dtoOptional.orElse(null);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        approveRoleService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<ApproveRoleDTO> list() {
        return approveRoleService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<ApproveRoleDTO> pageQuery(Pageable pageable) {
        return approveRoleService.findAll(pageable);
    }

/*    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public ApproveRoleDTO update(@PathVariable Long id,@RequestBody ApproveRoleDTO dto) {
        return approveRoleService.update(dto);
    }*/
}