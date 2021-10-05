package com.construction.feature.task.dto;

import com.construction.feature.task.domain.ContractType;
import com.construction.persistence.domain.SimpleObjectStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class BOQData {
    private Long id;
    private String code;
    private Long projectId;
    private String projectName;
    private Long houseId;
    private ContractType contractType;
    private String houseHouseNo;
    private Long streetId;
    private String streetName;
    private String details;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private SimpleObjectStatus status;
}
