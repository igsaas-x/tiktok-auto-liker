package com.construction.user.authentication.service;

import com.construction.user.authentication.domain.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class UserAuthentication extends User {

    private final AppUser appUser;

    public UserAuthentication(String username, String password, Collection<? extends GrantedAuthority> authorities, AppUser appUser) {
        super(username, password, authorities);
        this.appUser = appUser;
    }
}
