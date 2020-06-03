package com.construction.organization.workflow.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkFlowNodeDto {
    @NotNull
    private Long workFlowId;
    private String name;
    private Long parentId;
    private Long roleId;
    private String description;
    private boolean first;
    private boolean last;
}