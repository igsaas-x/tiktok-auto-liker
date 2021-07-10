package com.construction.user.authentication.service;

import com.construction.user.authentication.domain.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService service;

    @Override
    public UserAuthentication loadUserByUsername(final String name) throws UsernameNotFoundException {
        final var appUser = service.getUserByUserName(name);
        if (!UserStatus.ACTIVE.equals(appUser.getStatus())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not active");
        }
        return new UserAuthentication(appUser.getUserName(), appUser.getPassword(), service.grantedAuthorities(appUser), appUser);
    }
}
