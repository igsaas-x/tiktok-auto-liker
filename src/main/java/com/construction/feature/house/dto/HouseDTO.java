package com.construction.feature.house.dto;

import com.construction.feature.project.domain.Project;
import lombok.Data;

@Data
public class HouseDTO {
    private Project project;
    private String typeOfHouse;
    private String street;
    private String houseNo;
    private Float houseWidth;
    private Float houseLong;
    private Float landWidth;
    private Float landLong;
}