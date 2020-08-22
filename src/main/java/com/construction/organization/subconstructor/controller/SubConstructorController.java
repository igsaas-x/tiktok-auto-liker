package com.construction.organization.subconstructor.controller;

import com.construction.feature.FilterType;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.services.SubConstructorService;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subconstructor")
@AllArgsConstructor
public class SubConstructorController {

    private final SubConstructorService service;
    private final FilterConfig filterConfig;

    @GetMapping("/page")
    public Page<SubConstructor> getAll(Pageable pageable, FilterType filter) {
        filterConfig.configureFilter(ActionName.READ, "sub_constructor");
        return service.getAll(pageable, filter);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(SubConstructor subConstructor, Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "sub_constructor");
        return service.search(subConstructor, pageable);
    }

    @GetMapping
    public List<SubConstructor> getAll() {
        filterConfig.configureFilter(ActionName.READ, "sub_constructor");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public SubConstructor getById(@PathVariable final Long id) {
        filterConfig.configureFilter(ActionName.READ, "sub_constructor");
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ALL_PAYMENT')")
    public SubConstructor create(@RequestBody SubConstructor subConstructor) {
        return service.create(subConstructor);
    }

    @PutMapping("/{id}")
    public SubConstructor update(@PathVariable Long id, @RequestBody SubConstructor subConstructor) {
        filterConfig.configureFilter(ActionName.UPDATE, "sub_constructor");
        return service.update(id, subConstructor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "sub_constructor");
        service.delete(id);
    }

    @GetMapping("/verify/pending")
    public List<SubConstructor> getPendingForVerify() {
        filterConfig.configureFilter(ActionName.VERIFY, "sub_constructor");
        return service.getPendingForVerify();
    }

    @GetMapping("/approve/pending")
    public List<SubConstructor> getPendingForApprove() {
        filterConfig.configureFilter(ActionName.APPROVE, "sub_constructor");
        return service.getPendingForApprove();
    }

    @PostMapping("/{id}/verify")
    public SubConstructor verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "sub_constructor");
        return service.verify(id);
    }

    @PostMapping("/batch/verify")
    public boolean verifyAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.VERIFY, "sub_constructor");
        ids.getIds().forEach(service::verify);
        return true;
    }

    @PostMapping("/{id}/approve")
    public SubConstructor approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "sub_constructor");
        return service.approve(id);
    }

    @PostMapping("/batch/approve")
    public boolean approveAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.APPROVE, "sub_constructor");
        ids.getIds().forEach(service::approve);
        return true;
    }
}
