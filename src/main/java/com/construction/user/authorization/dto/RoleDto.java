package com.construction.user.authorization.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    private String name;
    private List<Long> permissionIds;
}
