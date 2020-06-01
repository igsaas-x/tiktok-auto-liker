package com.construction.feature.task.dto;

import com.construction.feature.task.domain.TaskBelongTo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TaskDto {
    private String typeOfWork;
    @NotNull
    private TaskBelongTo belongTo;
    private Long projectId;
    private Long streetId;
    private Long houseId;
    private String code;
    private String name;
    private String description;
    private Long parentId;
    private boolean leaf;
    private String floor;
    private Long boqId;
    private String contractType;
    private Integer quantity;
    private String unit;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal actualPrice;
}
