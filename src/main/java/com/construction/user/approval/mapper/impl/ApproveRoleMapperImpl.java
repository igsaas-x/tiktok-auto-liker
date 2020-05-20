package com.construction.user.approval.mapper.impl;

import com.construction.user.approval.domain.ApproveRole;
import com.construction.user.approval.dto.ApproveRoleDTO;
import com.construction.user.approval.mapper.ApproveRoleMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApproveRoleMapperImpl implements ApproveRoleMapper {
    @Override
    public ApproveRole toEntity(ApproveRoleDTO dto) {
        ApproveRole entity = new ApproveRole();
        entity.setName(dto.getName());
        entity.setUsers(dto.getUsers());
        return entity;
    }

    @Override
    public ApproveRoleDTO toDto(ApproveRole entity) {
        ApproveRoleDTO dto = new ApproveRoleDTO();
        dto.setName(entity.getName());
        dto.setUsers(entity.getUsers());
        return dto;
    }

    @Override
    public List<ApproveRole> toEntityList(List<ApproveRoleDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<ApproveRoleDTO> toDtoList(List<ApproveRole> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}