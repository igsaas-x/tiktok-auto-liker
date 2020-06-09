package com.construction.feature.house.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"id", "createdBy", "updatedBy", "verifiedBy", "approvedBy", "projectObjectName", "streetName"}, allowGetters = true)
public class HouseDto {
    private Long id;
    private Long projectId;
    private String projectObjectName;
    private Long streetId;
    private String streetName;
    private Long typeOfHouseId;
    private String houseNo;
    private Float houseWidth;
    private Float houseLong;
    private Float landWidth;
    private Float landLong;
    private String createdBy;
    private String updatedBy;
    private String verifiedBy;
    private String approvedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime verifiedAt;
    private LocalDateTime approvedAt;
}
