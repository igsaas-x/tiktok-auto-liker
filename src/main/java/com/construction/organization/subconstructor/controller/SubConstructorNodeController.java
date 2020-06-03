package com.construction.organization.subconstructor.controller;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.domain.SubConstructorWorkFlowNode;
import com.construction.organization.subconstructor.services.SubConstructorNodeService;
import com.construction.organization.subconstructor.services.SubConstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/subconstructor/workflow")
public class SubConstructorNodeController {

    @Autowired
    private SubConstructorNodeService service;
    @Autowired
    private SubConstructorService subConstructorService;
    @Autowired
    private ApplicationSecurityContext context;

    @PutMapping("/verify/role/{id}")
    public SubConstructorWorkFlowNode updateVerifyRole(@PathVariable Long id) {
        return service.updateVerifyRole(id);
    }

    @PutMapping("/approve/role/{id}")
    public SubConstructorWorkFlowNode updateApproveRole(@PathVariable Long id) {
        return service.updateApproveRole(id);
    }

    @GetMapping("/verify/role")
    public SubConstructorWorkFlowNode getVerifyRole() {
        return service.getVerifyNode();
    }

    @GetMapping("/verify/pending")
    public List<SubConstructor> getPendingForVerify() {
        if (!userHasRoleForVerify()) return null;
        return subConstructorService.getPendingForVerify();
    }

    @GetMapping("/approve/role")
    public SubConstructorWorkFlowNode getApproveRole() {
        return service.getApproveNode();
    }

    @GetMapping("/approve/pending")
    public List<SubConstructor> getPendingForApprove() {
        if (!userHasRoleForApprove()) return null;
        return subConstructorService.getPendingForApprove();
    }

    @PostMapping("/verify/{id}")
    public SubConstructor verify(@PathVariable Long id) {
        if (!userHasRoleForVerify()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user's role is not valid for this action");
        }
        return subConstructorService.verify(id);
    }

    @PostMapping("/approve/{id}")
    public SubConstructor approve(@PathVariable Long id) {
        if (!userHasRoleForApprove()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user's role is not valid for this action");
        }
        return subConstructorService.approve(id);
    }

    private boolean userHasRoleForVerify() {
        var roleId = context.authenticatedUser().getRole().getId();
        return service.getVerifyNode().getRole().getId().equals(roleId);
    }

    private boolean userHasRoleForApprove() {
        var roleId = context.authenticatedUser().getRole().getId();
        return service.getApproveNode().getRole().getId().equals(roleId);
    }
}
