package com.construction.appconfiguration;

import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authentication.repository.AppUserRepository;
import com.construction.user.authentication.service.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationSecurityContext {

    @Autowired
    private AppUserRepository repository;

    public Object getAuthPrinciple() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication auth = context == null ? null : context.getAuthentication();
        return auth == null ? null : auth.getPrincipal();
    }

    public AppUser authenticatedUser() {
        try {
            var principle = getAuthPrinciple();
            if (principle instanceof UserDetailsImpl) {
                return ((UserDetailsImpl) principle).getAppUser();
            } else if (principle instanceof String && !principle.equals("anonymous")) {
                return repository.findByUserName((String) principle).orElse(null);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
