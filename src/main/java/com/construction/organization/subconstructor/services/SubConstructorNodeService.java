package com.construction.organization.subconstructor.services;

import com.construction.organization.subconstructor.domain.SubConstructorWorkFlowNode;
import com.construction.organization.subconstructor.repository.SubConstructorWorkFlowNodeRepository;
import com.construction.user.authorization.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubConstructorNodeService {

    private static final Long VERIFY = 1L;
    private static final Long APPROVE = 2L;

    @Autowired
    private SubConstructorWorkFlowNodeRepository repository;
    @Autowired
    private UserRoleService roleService;

    public SubConstructorWorkFlowNode getVerifyNode() {
        return repository.findById(VERIFY).orElseThrow();
    }

    public SubConstructorWorkFlowNode updateVerifyRole(Long roleId) {
        var node = getVerifyNode();
        var role = roleService.getById(roleId);
        node.setRole(role);
        return repository.save(node);
    }

    public SubConstructorWorkFlowNode getApproveNode() {
        return repository.findById(APPROVE).orElseThrow();
    }

    public SubConstructorWorkFlowNode updateApproveRole(Long roleId) {
        var node = getApproveNode();
        var role = roleService.getById(roleId);
        node.setRole(role);
        return repository.save(node);
    }
}
