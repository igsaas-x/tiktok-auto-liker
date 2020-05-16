package com.construction.user.authorization.repositories;
import com.construction.user.authorization.domain.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.construction.user.authorization.domain.RolePermission;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission,Long>{
    List<RolePermission> findByUserRole(final UserRole role);
}