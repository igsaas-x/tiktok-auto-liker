package com.construction.feature.task.dto;

import com.construction.feature.task.domain.TaskBelongTo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"id", "createdBy", "updatedBy", "verifiedBy", "approvedBy"}, allowGetters = true)
public class TaskDto {
    private Long id;
    private String typeOfWork;
    @NotNull
    private TaskBelongTo belongTo;
    private Long projectId;
    private String projectObjectName;
    private Long streetId;
    private String streetName;
    private Long houseId;
    private String houseNo;
    private String code;
    private String name;
    private String description;
    private Long parentId;
    private boolean leaf;
    private String floor;
    private Long boqId;
    private String boqCode;
    private String contractType;
    private Integer quantity;
    private String unit;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal actualPrice;
    private String createdBy;
    private String updatedBy;
    private String verifiedBy;
    private String approvedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime verifiedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedAt;
}
