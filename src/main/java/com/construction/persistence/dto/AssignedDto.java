package com.construction.persistence.dto;

import com.construction.persistence.domain.AssignEntity;
import com.construction.persistence.domain.AssignFor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignedDto {
    private Long userId;
    private String userName;
    private LocalDateTime createdAt;
    private AssignFor assignFor;

    public static AssignedDto fromEntity(final AssignEntity assignEntity) {
        var dto = new AssignedDto();
        dto.setAssignFor(assignEntity.getAssignFor());
        dto.setCreatedAt(assignEntity.getCreatedAt());
        dto.setUserId(assignEntity.getAppUser().getId());
        dto.setUserName(assignEntity.getAppUser().getUserName());
        return dto;
    }
}
