package com.construction.user.authorization.service;

import com.construction.user.authorization.domain.Permission;
import com.construction.user.authorization.domain.RolePermission;
import com.construction.user.authorization.domain.UserRole;
import com.construction.user.authorization.dto.PermissionDto;
import com.construction.user.authorization.dto.RoleDto;
import com.construction.user.authorization.dto.mapper.PermissionMapper;
import com.construction.user.authorization.repository.PermissionRepository;
import com.construction.user.authorization.repository.RolePermissionRepository;
import com.construction.user.authorization.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserRoleService {

    private final UserRoleRepository repository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionMapper permissionMapper;
    private final PermissionService permissionService;
    private final PermissionRepository permissionRepository;

    @CachePut("roles")
    public UserRole save(RoleDto roleDto) {
        var role = new UserRole().setName(roleDto.getName());
        final var newRole = repository.save(role);
        if (roleDto.getPermissions() != null) {
            var permissionIds = roleDto.getPermissions().stream()
                    .filter(PermissionDto::isSelected)
                    .map(PermissionDto::getId).collect(Collectors.toList());
            var permissions = permissionService.getAllByIds(permissionIds);
            var rolePermissions = permissions.stream()
                    .map(permission -> newRolePermission(newRole, permission))
                    .collect(Collectors.toList());
            rolePermissionRepository.saveAll(rolePermissions);
        }
        return newRole;
    }

    private RolePermission newRolePermission(final UserRole role, Permission permission) {
        return new RolePermission().setPermission(permission).setRole(role);
    }

    @Caching(evict = {
            @CacheEvict(value = "rolePermissions", key = "#id"),
            @CacheEvict(value = "role", key = "#id"),
            @CacheEvict("roles"),
            @CacheEvict("usersAuthorities")
    })
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Cacheable(value = "role", key = "#id")
    public UserRole getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Cacheable("roles")
    public List<UserRole> getAll() {
        return repository.findAll();
    }

    public Page<UserRole> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Cacheable(value = "rolePermissions", key = "#id")
    public List<Permission> getRolePermission(Long id) {
        return permissionRepository.getPermissionByRoleId(id);
    }

    public List<PermissionDto> getRolePermissionDto(Long id) {
        final var role = getById(id);
        final var permissions = permissionRepository.getPermissionByRoleId(id);
        return permissionMapper.toRoleDto(role, permissions).getPermissions();
    }

    @Caching(evict = {
            @CacheEvict(value = "rolePermissions", key = "#id"),
            @CacheEvict(value = "role", key = "#id"),
            @CacheEvict("roles"),
            @CacheEvict(value = "usersAuthorities", allEntries = true)
    })
    public UserRole updateById(Long id, RoleDto dto) {
        var role = getById(id);
        role.setName(dto.getName());
        final var newRole = repository.save(role);
        final var permissionDtos = dto.getPermissions();
        if (permissionDtos != null) {
            var permissionIds = permissionDtos.stream()
                    .filter(PermissionDto::isSelected)
                    .map(PermissionDto::getId)
                    .collect(Collectors.toList());
            var permissions = permissionService.getAllByIds(permissionIds);
            var newRolePermissions = permissions.stream()
                    .map(permission -> newRolePermission(newRole, permission))
                    .collect(Collectors.toList());
            var oldRolePermissions = rolePermissionRepository.findAllByRole(role);
            rolePermissionRepository.deleteAll(oldRolePermissions);
            rolePermissionRepository.flush();
            rolePermissionRepository.saveAll(newRolePermissions);
        }
        return newRole;
    }
}