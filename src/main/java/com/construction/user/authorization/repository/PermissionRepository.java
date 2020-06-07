package com.construction.user.authorization.repository;

import com.construction.user.authorization.domain.ActionName;
import com.construction.user.authorization.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByActionName(final ActionName actionName);
}