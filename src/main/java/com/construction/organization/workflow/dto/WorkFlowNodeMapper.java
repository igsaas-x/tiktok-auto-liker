package com.construction.organization.workflow.dto;

import com.construction.organization.workflow.domain.WorkFlowNode;
import com.construction.organization.workflow.repository.WorkFlowNodeRepository;
import com.construction.organization.workflow.repository.WorkFlowRepository;
import com.construction.persistence.mapper.DtoMapper;
import com.construction.user.authorization.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkFlowNodeMapper implements DtoMapper<WorkFlowNodeDto, WorkFlowNode> {

    @Autowired
    private UserRoleService roleService;
    @Autowired
    private WorkFlowNodeRepository repository;
    @Autowired
    private WorkFlowRepository flowRepository;

    @Override
    public WorkFlowNode toEntity(WorkFlowNodeDto workFlowNodeDto) {
        var node = new WorkFlowNode()
                .setName(workFlowNodeDto.getName())
                .setDescription(workFlowNodeDto.getDescription())
                .setWorkFlow(flowRepository.findById(workFlowNodeDto.getWorkFlowId()).orElseThrow())
                .setRole(roleService.getById(workFlowNodeDto.getRoleId()));
        if (workFlowNodeDto.getParentId() != null) {
            var parent = repository.findById(workFlowNodeDto.getParentId()).orElseThrow();
            node.setParent(parent);
        }else {
            node.setFirst(true);
        }
        return node;
    }
}
