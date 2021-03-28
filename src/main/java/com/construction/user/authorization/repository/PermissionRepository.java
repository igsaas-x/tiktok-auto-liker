package com.construction.user.authorization.repository;

import com.construction.user.authorization.domain.ActionName;
import com.construction.user.authorization.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByActionName(final ActionName actionName);

    @Query(value = "SELECT p.* from role_permission rp , user_role ur , permission p where rp.role_id = ur.id and rp.permission_id = p.id and ur.id = :id",
            nativeQuery = true)
    List<Permission> getPermissionByRoleId(@Param("id") final Long id);
}