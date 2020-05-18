package com.construction.feature.task.dto;

import com.construction.feature.house.domain.House;
import com.construction.feature.project.domain.Project;
import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.Task;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskDTO {
    private String typeOfWork;
    private House house;
    private Project project;
    private String code;
    private String name;
    private String description;
    private Task parent;
    private boolean leaf;
    private String floor;
    private BOQ boq;
    private String contractType;
    private Integer quantity;
    private String unit;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal actualPrice;
}