package com.construction.user.authorization.dto.mapper;

import com.construction.user.authorization.domain.Permission;
import com.construction.user.authorization.domain.UserRole;
import com.construction.user.authorization.dto.PermissionDto;
import com.construction.user.authorization.dto.RoleDto;
import com.construction.user.authorization.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PermissionMapper {

    private final PermissionRepository repository;

    private PermissionDto toDto(Permission permission, boolean selected) {
        return new PermissionDto()
                .setId(permission.getId())
                .setActionName(permission.getActionName())
                .setCodeName(permission.getCodeName())
                .setDescription(permission.getDescription())
                .setEntityName(permission.getEntityName())
                .setSelected(selected);
    }

    public Permission toEntity(PermissionDto dto) {
        return new Permission()
                .setActionName(dto.getActionName())
                .setCodeName(dto.getCodeName())
                .setDescription(dto.getDescription())
                .setEntityName(dto.getEntityName());
    }

    public RoleDto toRoleDto(UserRole role, List<Permission> selectedPermissions) {
        var allPermissionIds = repository.findAll();
        var permissionDtos = allPermissionIds.stream()
                .map(permission -> toDto(permission, selectedPermissions.contains(permission)))
                .collect(Collectors.toList());
        return new RoleDto().setName(role.getName()).setPermissions(permissionDtos);
    }
}
