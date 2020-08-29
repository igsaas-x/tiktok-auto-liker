package com.construction.persistence.dto;

import lombok.Data;

import java.util.List;

@Data
public class IdListBatch {
    private List<Long> ids;
    private List<Long> sids;
}
