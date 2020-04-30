package com.construction.user.authentication.service;

import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authorization.domain.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final AppUser appUser;

    public UserDetailsImpl(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (appUser.getRole() == null) {
            return null;
        }
        if (appUser.getRole().getPermissions() == null) {
            return null;
        }
        return appUser.getRole().getPermissions().stream().map(this::getAuthority).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private GrantedAuthority getAuthority(Permission permission) {
        return new SimpleGrantedAuthority(permission.getCodeName());
    }

    public AppUser getAppUser() {
        return appUser;
    }
}
