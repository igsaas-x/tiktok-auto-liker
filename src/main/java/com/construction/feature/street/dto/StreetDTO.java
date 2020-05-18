package com.construction.feature.street.dto;

import com.construction.feature.project.domain.Project;
import lombok.Data;

@Data
public class StreetDTO {
    private String name;
    private Project project;
    private String description;
}