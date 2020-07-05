package com.construction.feature.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedBy;
}
