package com.construction.user.approval.mapper;

import com.construction.user.approval.domain.ApproveRole;
import com.construction.user.approval.dto.ApproveRoleDTO;
import com.construction.persistence.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApproveRoleMapper extends EntityMapper<ApproveRoleDTO, ApproveRole> {
}