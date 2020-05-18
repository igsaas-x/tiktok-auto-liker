package com.construction.user.authorization.mapper.impl;

import com.construction.user.authorization.domain.UserRole;
import com.construction.user.authorization.dto.UserRoleDTO;
import com.construction.user.authorization.mapper.UserRoleMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoleMapperImpl implements UserRoleMapper {
    @Override
    public UserRole toEntity(UserRoleDTO dto) {
        UserRole entity = new UserRole();
        entity.setName(dto.getName());
        entity.setPermissions(dto.getPermissions());
        return entity;
    }

    @Override
    public UserRoleDTO toDto(UserRole entity) {
        UserRoleDTO dto = new UserRoleDTO();
        dto.setName(entity.getName());
        dto.setPermissions(entity.getPermissions());
        return dto;
    }

    @Override
    public List<UserRole> toEntityList(List<UserRoleDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<UserRoleDTO> toDtoList(List<UserRole> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}