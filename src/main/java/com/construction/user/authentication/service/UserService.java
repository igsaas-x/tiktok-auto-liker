package com.construction.user.authentication.service;

import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authentication.repository.AppUserRepository;
import com.construction.user.authorization.domain.ActionName;
import com.construction.user.authorization.domain.Permission;
import com.construction.user.authorization.repository.PermissionRepository;
import com.construction.user.authorization.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@SuppressWarnings("unchecked")
public class UserService {

    public static final String ALL_PERMISSION = "ALL_ALL";
    public static final String READ_ALL_PERMISSION = "READ_ALL";
    private static final List<String> FULL_PERMISSION = List.of("CREATE",
            "UPDATE",
            "DELETE",
            "READ_WRITE",
            "VERIFY",
            "APPROVE",
            "ASSIGN",
            "ASSIGN_ASSIGNED");

    private final AppUserRepository repository;
    private final PermissionRepository permissionRepository;
    private final UserRoleService roleService;

    public List<SimpleGrantedAuthority> grantedAuthorities(AppUser user) {
        var role = user.getRole();
        if (role == null) {
            return Collections.EMPTY_LIST;
        }
        var permissions = roleService.getRolePermission(role.getId());
        if (!permissions.isEmpty()) {
            if (permissions.stream().anyMatch(permission -> permission.getCodeName().equals(ALL_PERMISSION))) {
                return allAuthorities();
            }
            if (permissions.stream().anyMatch(permission -> permission.getCodeName().equals(READ_ALL_PERMISSION))) {
                final var authorities = permissions
                        .stream()
                        .map(this::getAuthorityFromPermission)
                        .collect(Collectors.toList());
                authorities.addAll(readAllAuthorities());
                return authorities
                        .stream()
                        .distinct()
                        .collect(Collectors.toList());
            }
            final var authorities = permissions
                    .stream()
                    .map(this::getAuthorityFromPermission)
                    .collect(Collectors.toList());
            permissions
                    .stream()
                    .filter(permission -> permission.getActionName().equals(ActionName.FULL))
                    .forEach(permission -> {
                        authorities.addAll(getFullAuthorities(permission));
                    });
            return authorities.stream().distinct().collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    private List<SimpleGrantedAuthority> getFullAuthorities(final Permission permission) {
        return FULL_PERMISSION.stream()
                .map(p -> new SimpleGrantedAuthority(p + "_" + permission.getEntityName()))
                .collect(Collectors.toList());
    }

    private List<SimpleGrantedAuthority> allAuthorities() {
        return permissionRepository.findAll().stream()
                .map(this::getAuthorityFromPermission)
                .collect(Collectors.toList());
    }

    private List<SimpleGrantedAuthority> readAllAuthorities() {
        return permissionRepository.findAllByActionName(ActionName.READ).stream()
                .map(this::getAuthorityFromPermission)
                .collect(Collectors.toList());
    }

    private SimpleGrantedAuthority getAuthorityFromPermission(Permission permission) {
        return new SimpleGrantedAuthority(permission.getCodeName());
    }

    public AppUser getUserByUserName(final String name) {
        return repository.findByUserName(name).orElseThrow(() -> new ResourceNotFoundException(AppUser.class, name));
    }
}
