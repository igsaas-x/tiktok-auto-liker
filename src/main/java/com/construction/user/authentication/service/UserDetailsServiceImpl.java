package com.construction.user.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserService service;

    @Override
    public UserDetails loadUserByUsername(final String name) throws UsernameNotFoundException {
        final var appUser = service.getUserByUserName(name);
        return new UserDetailsImpl(appUser);
    }
}
