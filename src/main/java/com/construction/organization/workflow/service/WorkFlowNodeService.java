package com.construction.organization.workflow.service;

import com.construction.organization.workflow.domain.WorkFlowNode;
import com.construction.organization.workflow.dto.WorkFlowNodeDto;
import com.construction.organization.workflow.dto.WorkFlowNodeMapper;
import com.construction.organization.workflow.repository.WorkFlowNodeRepository;
import com.construction.organization.workflow.repository.WorkFlowRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class WorkFlowNodeService {

    @Autowired
    private WorkFlowNodeRepository repository;
    @Autowired
    private WorkFlowRepository flowRepository;
    @Autowired
    private WorkFlowNodeMapper mapper;
    @Autowired
    private EntityDataMapper dataMapper;

    public WorkFlowNode getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(WorkFlowNode.class, id));
    }

    public List<WorkFlowNode> getByWorkFlowName(String name) {
        return repository.findAllByWorkFlowName(name);
    }

    public WorkFlowNode create(WorkFlowNodeDto dto) {
        var node = mapper.toEntity(dto);
        var parent = node.getParent();
        if (parent != null) {
            parent.setLast(false);
            repository.save(parent);
        } else { // parent is null, first node
            var flow = node.getWorkFlow();
            if (flow.getStartBy() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("work flow name: %s already have first node", flow.getName()));
            } else {
                flow.setStartBy(node);
                flowRepository.save(flow);
            }
        }
        return repository.save(node);
    }

    public WorkFlowNode update(Long id, WorkFlowNodeDto dto) {
        var target = getById(id);
        var source = mapper.toEntity(dto);
        target = dataMapper.mapObject(source, target, WorkFlowNode.class);
        return repository.save(target);
    }

    public void delete(Long id) {
        var node = getById(id);
        if (!node.isLast()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot delete middle node");
        } else {
            var parent = node.getParent();
            var flow = node.getWorkFlow();
            if (parent != null) {
                parent.setLast(true);
                repository.save(parent);
                flow.setEndBy(parent);
                flowRepository.save(flow);
            }else { // parent is null -- work flow become empty
                flow.setStartBy(null);
                flow.setEndBy(null);
            }
        }
        repository.delete(node);
    }
}
