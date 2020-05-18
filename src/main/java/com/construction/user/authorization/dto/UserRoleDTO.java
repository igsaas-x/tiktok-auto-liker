package com.construction.user.authorization.dto;

import com.construction.user.authorization.domain.Permission;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleDTO {
    private String name;
    private List<Permission> permissions;
}