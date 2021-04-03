package com.construction.organization.subconstructor.controller;

import com.construction.feature.FilterType;
import com.construction.organization.subconstructor.data.SubConstructorData;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.repository.SubConstructorDataRepository;
import com.construction.organization.subconstructor.services.SubConstructorService;
import com.construction.persistence.dto.IdList;
import com.construction.persistence.filter.FilterConfig;
import com.construction.user.authorization.domain.ActionName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subconstructor")
@RequiredArgsConstructor
public class SubConstructorController {

    private final SubConstructorService service;
    private final FilterConfig filterConfig;
    private final SubConstructorDataRepository dataRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('READ_SUB_CONSTRUCTOR')")
    public Page<SubConstructor> getAll(Pageable pageable, FilterType filter) {
        return service.getAll(pageable, filter);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('READ_SUB_CONSTRUCTOR')")
    public ResponseEntity<Object> search(SubConstructor subConstructor, Pageable pageable) {
        filterConfig.configureFilter(ActionName.READ, "sub_constructor");
        return service.search(subConstructor, pageable);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_SUB_CONSTRUCTOR')")
    public List<SubConstructor> getAll() {
        filterConfig.configureFilter(ActionName.READ, "sub_constructor");
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_SUB_CONSTRUCTOR')")
    public SubConstructor getById(@PathVariable final Long id) {
        filterConfig.configureFilter(ActionName.READ, "sub_constructor");
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_SUB_CONSTRUCTOR')")
    public SubConstructor create(@RequestBody SubConstructor subConstructor) {
        return service.create(subConstructor);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_SUB_CONSTRUCTOR')")
    public SubConstructor update(@PathVariable Long id, @RequestBody SubConstructor subConstructor) {
        filterConfig.configureFilter(ActionName.UPDATE, "sub_constructor");
        return service.update(id, subConstructor);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_SUB_CONSTRUCTOR')")
    public void delete(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.DELETE, "sub_constructor");
        service.delete(id);
    }

    @GetMapping("/verify/pending")
    @PreAuthorize("hasAuthority('READ_SUB_CONSTRUCTOR')")
    public List<SubConstructor> getPendingForVerify() {
        filterConfig.configureFilter(ActionName.VERIFY, "sub_constructor");
        return service.getPendingForVerify();
    }

    @GetMapping("/approve/pending")
    @PreAuthorize("hasAuthority('READ_SUB_CONSTRUCTOR')")
    public List<SubConstructor> getPendingForApprove() {
        filterConfig.configureFilter(ActionName.APPROVE, "sub_constructor");
        return service.getPendingForApprove();
    }

    @PostMapping("/{id}/verify")
    @PreAuthorize("hasAuthority('VERIFY_SUB_CONSTRUCTOR')")
    public SubConstructor verify(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.VERIFY, "sub_constructor");
        return service.verify(id);
    }

    @PostMapping("/batch/verify")
    @PreAuthorize("hasAuthority('VERIFY_SUB_CONSTRUCTOR')")
    public boolean verifyAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.VERIFY, "sub_constructor");
        ids.getIds().forEach(service::verify);
        return true;
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_SUB_CONSTRUCTOR')")
    public SubConstructor approve(@PathVariable Long id) {
        filterConfig.configureFilter(ActionName.APPROVE, "sub_constructor");
        return service.approve(id);
    }

    @PostMapping("/batch/approve")
    @PreAuthorize("hasAuthority('APPROVE_SUB_CONSTRUCTOR')")
    public boolean approveAll(@RequestBody IdList ids) {
        filterConfig.configureFilter(ActionName.APPROVE, "sub_constructor");
        ids.getIds().forEach(service::approve);
        return true;
    }

    @GetMapping("fingerprint")
    public List<SubConstructorData> getAllData() {
        return dataRepository.findAll();
    }

    @GetMapping("fingerprint/pending")
    public List<SubConstructorData> getPendingData() {
        return dataRepository.findByBase64(null);
    }

    @PostMapping("fingerprint")
    public SubConstructorData setFingerPrint(@RequestBody SubConstructorData subConstructorData) {
        var subConstructor = service.addFingerPrint(subConstructorData);
        return new SubConstructorData()
                .setId(subConstructor.getId())
                .setEngFullName(subConstructor.getEngFullName());
    }

    @PostMapping("{id}/fingerprint/verify")
    public SubConstructorData verifyFingerprint(@PathVariable Long id) {
        var subConstructor = service.getById(id);
        var subConstructorData = new SubConstructorData()
                .setId(subConstructor.getId())
                .setEngFullName(subConstructor.getEngFullName());
        messagingTemplate.convertAndSend("/topic/subconstructor/fingerprint", subConstructorData);
        return subConstructorData;
    }
}
