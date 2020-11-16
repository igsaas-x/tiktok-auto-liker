package com.construction.persistence.dto;

import lombok.Data;

import java.util.List;

@Data
public class IdListUserListBatch {
    private List<Long> ids;
    private List<Long> userIds;
}
