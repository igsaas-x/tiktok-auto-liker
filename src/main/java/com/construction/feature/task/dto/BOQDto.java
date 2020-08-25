package com.construction.feature.task.dto;

import com.construction.persistence.domain.SimpleObjectStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"id", "createdBy", "updatedBy", "createdAt", "updatedAt", "status"}, allowGetters = true)
public class BOQDto {
    private Long id;
    private String code;
    private Long projectId;
    private Long houseId;
    private Long streetId;
    private String details;
    @JsonProperty("createdBy")
    private String createdByUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonProperty("updatedBy")
    private String updatedByUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private SimpleObjectStatus status;
    private List<TaskDto> tasks;
}
