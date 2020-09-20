package com.construction.basic.config.controller;

import com.construction.basic.config.dto.SystemConfigDTO;
import com.construction.basic.config.service.SystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/system-config")
@RestController
@Api(tags = "SystemConfig API")
@RequiredArgsConstructor
public class SystemConfigController {

    private final SystemConfigService systemConfigService;

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody SystemConfigDTO systemConfig) {
        systemConfigService.save(systemConfig);
    }

    @GetMapping("/{id}")
    public SystemConfigDTO findById(@PathVariable("id") Long id) {
        Optional<SystemConfigDTO> dtoOptional = systemConfigService.findById(id);
        return dtoOptional.orElse(null);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        systemConfigService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping
    public List<SystemConfigDTO> list() {
        return systemConfigService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page")
    public Page<SystemConfigDTO> pageQuery(Pageable pageable) {
        return systemConfigService.findAll(pageable);
    }
}