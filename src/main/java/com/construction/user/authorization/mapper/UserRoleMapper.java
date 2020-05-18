package com.construction.user.authorization.mapper;

import com.construction.persistence.mapper.EntityMapper;
import com.construction.user.authorization.domain.UserRole;
import com.construction.user.authorization.dto.UserRoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper extends EntityMapper<UserRoleDTO, UserRole> {
}