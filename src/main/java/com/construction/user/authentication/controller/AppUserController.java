package com.construction.user.authentication.controller;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authentication.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    private AppUserService service;
    @Autowired
    private EntityDataMapper entityDataMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ApplicationSecurityContext context;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_USER')")
    public List<AppUser> getAllUser() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ALL_USER')")
    public AppUser getUserById(@NotNull @PathVariable("id") final Long id) {
        return service.getById(id);
    }

    @GetMapping("/current")
    public AppUser getCurrentUser() {
        final var user = context.authenticatedUser();
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "unauthorized request");
        }
        return user;
    }

    @PutMapping("/changePassword")
    public AppUser changePassword(@RequestParam("old") final String oldPassword,
                                  @RequestParam("new") final String newPassword) {
        return service.changePassword(oldPassword, newPassword);
    }

    @PutMapping("/{id}")
    public AppUser updateUser(@NotNull @PathVariable final Long id, @RequestBody final AppUser sourceUser) {
        final var targetUser = service.getById(id);
        try {
            final var user = entityDataMapper.mapObject(sourceUser, targetUser, AppUser.class);
            return service.updateUser(user);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }
    }

    @PutMapping("/{id}/role/{roleId}")
    public AppUser assignRole(@PathVariable Long id, @PathVariable Long roleId) {
        return service.assignRole(id, roleId);
    }

    @PostMapping
    public AppUser createUser(@NotNull @RequestBody final AppUser appUser) {
        return service.createUser(appUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@NotNull @PathVariable("id") final Long id) {
        service.deleteById(id);
    }
}
