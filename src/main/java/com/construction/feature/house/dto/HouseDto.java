package com.construction.feature.house.dto;

import lombok.Data;

@Data
public class HouseDto {
    private Long projectId;
    private Long streetId;
    private Long typeOfHouseId;
    private String houseNo;
    private Float houseWidth;
    private Float houseLong;
    private Float landWidth;
    private Float landLong;
}
