package com.construction.feature.task.controller;

import com.construction.feature.task.dto.BOQDTO;
import com.construction.feature.task.service.BOQService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/bOQ")
@RestController
@Api(tags = "BOQ API")
public class BOQController {
    private final BOQService bOQService;

    public BOQController(BOQService bOQService) {
        this.bOQService = bOQService;
    }

    @ApiOperation("Add new data")
    @PostMapping("/save")
    public void save(@RequestBody BOQDTO bOQ) {
        bOQService.save(bOQ);
    }

    @ApiOperation("Delete based on primary key")
    @GetMapping("/{id}")
    public BOQDTO findById(@PathVariable("id") Long id) {
        Optional<BOQDTO> dtoOptional = bOQService.findById(id);
        return dtoOptional.orElse(null);
    }

    @ApiOperation("Find by Id")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        bOQService.deleteById(id);
    }

    @ApiOperation("Find all data")
    @GetMapping("/list")
    public List<BOQDTO> list() {
        return bOQService.findAll();
    }

    @ApiOperation("Pagination request")
    @GetMapping("/page-query")
    public Page<BOQDTO> pageQuery(Pageable pageable) {
        return bOQService.findAll(pageable);
    }

/*    @ApiOperation("Update one data")
    @PutMapping("/update/{id}")
    public BOQDTO update(@PathVariable Long id, @RequestBody BOQDTO dto) {
        return bOQService.updateById(id);
    }*/
}