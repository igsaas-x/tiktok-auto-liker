package com.construction.feature.street.controller;

import com.construction.feature.FilterType;
import com.construction.feature.street.domain.Street;
import com.construction.feature.street.domain.StreetAssign;
import com.construction.feature.street.repository.StreetRepository;
import com.construction.feature.street.service.StreetAssignService;
import com.construction.feature.street.service.StreetService;
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
import java.util.Map;

@Slf4j
@RequestMapping("/street")
@RestController
@Api(tags = "Street API")
@RequiredArgsConstructor
public class StreetController {

    private final StreetRepository repository;
    private final StreetService service;
    private final FilterConfig filterConfig;
    private final StreetAssignService assignService;

    @ApiOperation("Add new data")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_STREET')")
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
    @DeleteMapping("/{id}")
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

    @GetMapping("/pending")
    public Page<Street> getAllPending(Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "street");
        return service.getAllPending(pageable);
    }

    @GetMapping("/assigned")
    public List<Street> getAssignedStreet() {
        filterConfig.configureFilter(ActionName.READ, "street");
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
    public StreetAssign assign(@PathVariable Long id, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "street");
        return service.assign(id, userId, assignFor);
    }

    @PostMapping("/batch/assign/{userId}")
    public boolean assignAll(@RequestBody IdList ids, @PathVariable Long userId, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "street");
        ids.getIds().forEach(id -> service.assign(id, userId, assignFor));
        return true;
    }

    @PostMapping("/batch/assign")
    public boolean assignAllBatch(@RequestBody IdListUserListBatch body, @RequestParam AssignFor assignFor) {
        filterConfig.configureFilter(ActionName.ASSIGN, "street");
        body.getUserIds().forEach(userId -> {
            body.getIds().forEach(id -> {
                try {
                    service.assign(id, userId, assignFor);
                } catch (final Exception e) {
                    log.error(String.format("cannot assign street id %s to user id %s", id, userId));
                }
            });
        });
        return true;
    }

    @PostMapping("/{id}/unassign/{userId}")
    public void unAssign(@PathVariable Long id, @PathVariable Long userId) {
        filterConfig.configureFilter(ActionName.ASSIGN, "street");
        service.unAssign(id, userId);
    }

    @PostMapping("/{id}/verify")
    public Street verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "street");
        return service.verify(id);
    }

    @PostMapping("/batch/verify")
    public List<Street> verifyAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.VERIFY, "street");
        return service.verifyAll(ids.getIds());
    }

    @PostMapping("/{id}/approve")
    public Street approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "street");
        return service.approve(id);
    }

    @PostMapping("/batch/approve")
    public List<Street> approveAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.APPROVE, "street");
        return service.approveAll(ids.getIds());
    }

    @GetMapping("/{id}/assigned/user")
    public List<AssignedDto> getAssignedUser(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.READ, "street");
        return assignService.getAssignedUser(id);
    }

    @GetMapping("/code/{code:.+}")
    public Map<String, Object> codeExist(@PathVariable("code") String code) {
        System.out.println("code is:" + code);
        return Map.of("exist", repository.findByCode(code).isPresent());
    }
}