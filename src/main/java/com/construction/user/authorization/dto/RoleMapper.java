package com.construction.user.authorization.dto;

import com.construction.persistence.mapper.DtoMapper;
import com.construction.user.authorization.domain.UserRole;
import com.construction.user.authorization.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements DtoMapper<RoleDto, UserRole> {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UserRole toEntity(RoleDto roleDto) {
        var permissions = permissionRepository.findAllById(roleDto.getPermissionIds());
        return new UserRole().setName(roleDto.getName()).setPermissions(permissions);
    }
}
