package com.construction.user.authentication.service;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.exception.PasswordInvalidException;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authentication.repository.AppUserRepository;
import com.construction.user.authorization.domain.Permission;
import com.construction.user.authorization.domain.RolePermission;
import com.construction.user.authorization.repositories.PermissionRepository;
import com.construction.user.authorization.repositories.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class AppUserService {

    private static final String ALL_PERMISSION = "ALL_ALL_ALL";

    @Autowired
    private AppUserRepository repository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ApplicationSecurityContext context;
    @Autowired
    private PasswordEncoder encoder;

    public AppUser getUserByUserName(final String name) {
        return repository.findByUserName(name).orElseThrow(() -> new ResourceNotFoundException(AppUser.class, name));
    }

    public AppUser getUserByEmail(@Email @NotNull final String email) {
        return repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppUser.class, email));
    }

    public AppUser changePassword(final String oldPass, final String newPass) {
        final var user = context.authenticatedUser();
        if (user != null && encoder.matches(oldPass, user.getPassword())) {
            final var appUser = repository.findById(user.getId()).orElseThrow();
            appUser.setPassword(encoder.encode(newPass));
            return repository.save(appUser);
        }
        throw new PasswordInvalidException();
    }

    public AppUser updateUser(final AppUser appUser) {
        if (appUser.getPassword() != null) {
            appUser.setPassword(encoder.encode(appUser.getPassword()));
        }
        return repository.save(appUser);
    }

    public AppUser createUser(final AppUser appUser) {
        appUser.setPassword(encoder.encode(appUser.getPassword()));
        return repository.save(appUser);
    }

    public List<SimpleGrantedAuthority> grantedAuthoritySet(AppUser user) {
        var rolePermissions = rolePermissionRepository.findByUserRole(user.getRole());
        if (!rolePermissions.isEmpty()) {
            var permissionAll = rolePermissions.stream()
                    .filter(rolePermission -> rolePermission.getPermission().getCodeName().equals(ALL_PERMISSION))
                    .findAny();
            if (permissionAll.isPresent()) {
                return getAllAuthority();
            }
            return rolePermissions.stream().map(this::getAuthority).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    private SimpleGrantedAuthority getAuthority(RolePermission rolePermission) {
        return getAuthorityFromPermission(rolePermission.getPermission());
    }

    private List<SimpleGrantedAuthority> getAllAuthority() {
        return permissionRepository.findAll().stream()
                .map(this::getAuthorityFromPermission)
                .collect(Collectors.toList());
    }

    private SimpleGrantedAuthority getAuthorityFromPermission(Permission permission) {
        return new SimpleGrantedAuthority(permission.getCodeName());
    }
}
