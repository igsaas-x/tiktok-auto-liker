package com.construction.persistence.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SidList {
    @NotNull
    private List<Long> sids;
}
