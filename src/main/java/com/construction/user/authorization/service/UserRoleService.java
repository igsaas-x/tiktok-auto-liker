package com.construction.user.authorization.service;

import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.user.authorization.domain.Permission;
import com.construction.user.authorization.domain.RolePermission;
import com.construction.user.authorization.domain.UserRole;
import com.construction.user.authorization.dto.RoleDto;
import com.construction.user.authorization.repository.PermissionRepository;
import com.construction.user.authorization.repository.RolePermissionRepository;
import com.construction.user.authorization.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserRoleService {

    @Autowired
    private UserRoleRepository repository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private EntityDataMapper dataMapper;

    public UserRole save(RoleDto roleDto) {
        var role = new UserRole().setName(roleDto.getName());
        var permissions = roleDto.getPermissionIds().stream().map(this::getPermissionById).collect(Collectors.toList());
        role = repository.save(role);
        var rolePermissions = newRolePermission(role, permissions);
        rolePermissionRepository.saveAll(rolePermissions);
        return role;
    }

    private Permission getPermissionById(Long id) {
        return permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Permission.class, id));
    }

    private List<RolePermission> newRolePermission(final UserRole role, List<Permission> permissions) {
        return permissions.stream()
                .map(permission -> new RolePermission().setPermission(permission).setRole(role))
                .collect(Collectors.toList());
    }

    public void save(List<UserRole> dtos) {
        repository.saveAll(dtos);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public UserRole getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<UserRole> getAll() {
        return repository.findAll();
    }

    public Page<UserRole> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Permission> getRolePermission(Long id) {
        return rolePermissionRepository.findAllByRoleId(id)
                .stream()
                .map(RolePermission::getPermission)
                .collect(Collectors.toList());
    }

    public UserRole updateById(Long id, RoleDto dto) {
        var role = getById(id);
        role.setName(dto.getName());
        var permissions = dto.getPermissionIds().stream().map(this::getPermissionById).collect(Collectors.toList());
        role = repository.save(role);
        var rolePermissions = newRolePermission(role, permissions);
        var oldPermissions = rolePermissionRepository.findAllByRole(role);
        rolePermissionRepository.deleteAll(oldPermissions);
        rolePermissionRepository.saveAll(rolePermissions);
        return role;
    }
}