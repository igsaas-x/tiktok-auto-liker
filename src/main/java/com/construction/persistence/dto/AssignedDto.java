package com.construction.persistence.dto;

import com.construction.persistence.domain.AssignEntity;
import com.construction.persistence.domain.AssignFor;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

import static com.construction.persistence.dto.LocalDateFormat.DATE_TIME_FORMAT;

@Data
public class AssignedDto {
    private Long userId;
    private String userName;
    @JsonFormat(pattern = DATE_TIME_FORMAT)
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
