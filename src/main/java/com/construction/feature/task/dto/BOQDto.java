package com.construction.feature.task.dto;

import com.construction.persistence.domain.SimpleObjectStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"createdBy", "updatedBy", "createdAt", "updatedAt", "status"}, allowGetters = true)
public class BOQDto {
    private String code;
    private Long projectId;
    private Long houseId;
    private Long streetId;
    private String details;
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private SimpleObjectStatus status;
}
