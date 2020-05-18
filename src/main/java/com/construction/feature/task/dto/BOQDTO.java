package com.construction.feature.task.dto;

import com.construction.feature.house.domain.House;
import com.construction.feature.project.domain.Project;
import com.construction.feature.street.domain.Street;
import lombok.Data;

@Data
public class BOQDTO {
    private String code;
    private Project project;
    private House house;
    private Street street;
}