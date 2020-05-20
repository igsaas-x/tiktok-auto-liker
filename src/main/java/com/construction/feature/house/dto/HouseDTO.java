package com.construction.feature.house.dto;

import com.construction.feature.project.domain.Project;
import com.construction.feature.street.domain.Street;
import lombok.Data;

@Data
public class HouseDTO {
    private Project project;
    private String typeOfHouse;
    private Street street;
    private String houseNo;
    private Float houseWidth;
    private Float houseLong;
    private Float landWidth;
    private Float landLong;
}