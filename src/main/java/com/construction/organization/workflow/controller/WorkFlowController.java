package com.construction.organization.workflow.controller;

import com.construction.organization.workflow.domain.WorkFlow;
import com.construction.organization.workflow.domain.WorkFlowNode;
import com.construction.organization.workflow.repository.WorkFlowRepository;
import com.construction.organization.workflow.service.WorkFlowNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workflow")
public class WorkFlowController {

    private static final String PAYMENT_WORK_FLOW_NAME = "payment";
    private static final String SUB_CONSTRUCTOR_WORK_FLOW_NAME = "subconstructor";

    @Autowired
    private WorkFlowRepository repository;
    @Autowired
    private WorkFlowNodeService nodeService;

    @GetMapping
    public WorkFlow getByName(@RequestParam String name) {
        return repository.findByName(name).orElse(null);
    }

    @GetMapping("/all")
    public List<WorkFlow> getAll() {
        return repository.findAll();
    }

    @GetMapping("/" + SUB_CONSTRUCTOR_WORK_FLOW_NAME)
    public WorkFlow getSubConstructor() {
        return repository.findByName(SUB_CONSTRUCTOR_WORK_FLOW_NAME).orElseThrow();
    }

    @GetMapping("/" + SUB_CONSTRUCTOR_WORK_FLOW_NAME + "/nodes")
    public List<WorkFlowNode> getSubConstructorFlow() {
        return nodeService.getByWorkFlowName(SUB_CONSTRUCTOR_WORK_FLOW_NAME);
    }

    @GetMapping("/" + PAYMENT_WORK_FLOW_NAME)
    private WorkFlow getPayment() {
        return repository.findByName(PAYMENT_WORK_FLOW_NAME).orElseThrow();
    }

    @GetMapping("/" + PAYMENT_WORK_FLOW_NAME + "/nodes")
    public List<WorkFlowNode> getPaymentFlow() {
        return nodeService.getByWorkFlowName(PAYMENT_WORK_FLOW_NAME);
    }
}
