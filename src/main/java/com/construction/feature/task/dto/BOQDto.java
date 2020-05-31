package com.construction.feature.task.dto;

import lombok.Data;

@Data
public class BOQDto {
    private String code;
    private Long projectId;
    private Long houseId;
    private Long streetId;
    private String details;
}
