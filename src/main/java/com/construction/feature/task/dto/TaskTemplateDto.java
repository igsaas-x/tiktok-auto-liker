package com.construction.feature.task.dto;

import com.construction.feature.task.domain.ContractType;
import lombok.Data;

@Data
public class TaskTemplateDto {
    private Long parentId;
    private String parentName;
    private boolean firstLevel;
    private String code;
    private String name;
    private String description;
    private boolean leaf;
    private String floor;
}
