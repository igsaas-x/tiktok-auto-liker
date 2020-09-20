package com.construction.user.authorization.dto;

import com.construction.user.authorization.domain.ActionName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PermissionDto {
    private Long id;
    private ActionName actionName;
    private String entityName;
    private String codeName;
    private String description;
    private boolean selected;
}
