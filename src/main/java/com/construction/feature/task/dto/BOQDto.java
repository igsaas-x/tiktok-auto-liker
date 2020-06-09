package com.construction.feature.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"createdBy", "updatedBy"}, allowGetters = true)
public class BOQDto {
    private String code;
    private Long projectId;
    private Long houseId;
    private Long streetId;
    private String details;
    private String createdBy;
    private String updatedBy;
}
