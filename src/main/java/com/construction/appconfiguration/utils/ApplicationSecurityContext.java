package com.construction.appconfiguration.utils;

import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authentication.service.AppUserService;
import com.construction.user.authentication.service.UserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationSecurityContext {

    @Autowired
    private AppUserService service;

    public Object getAuthPrinciple() {
        final var context = SecurityContextHolder.getContext();
        final var authentication = context == null ? null : context.getAuthentication();
        return authentication == null ? null : authentication.getPrincipal();
    }

    public AppUser authenticatedUser() {
        try {
            var principle = getAuthPrinciple();
            if (principle instanceof UserAuthentication) {
                return ((UserAuthentication) principle).getAppUser();
            } else if (principle instanceof String && !principle.equals("anonymousUser")) {
                return service.getUserByUserName((String) principle);
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
