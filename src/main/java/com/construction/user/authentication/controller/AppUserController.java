package com.construction.user.authentication.controller;

import com.construction.appconfiguration.ApplicationSecurityContext;
import com.construction.user.authentication.repository.AppUserRepository;
import com.construction.user.authentication.service.AppUserService;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.user.authentication.domain.AppUser;
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
    private AppUserRepository repository;
    @Autowired
    private AppUserService service;
    @Autowired
    private EntityDataMapper entityDataMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ApplicationSecurityContext context;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AppUser> getAllUser() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AppUser getUserById(@NotNull @PathVariable("id") final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppUser.class, id));
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
        final var targetUser = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppUser.class, id));
        try {
            final var user = entityDataMapper.mapObject(sourceUser, targetUser, AppUser.class);
            return service.updateUser(user);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }
    }

    @PostMapping
    public AppUser createUser(@NotNull @RequestBody final AppUser appUser) {
        return service.createUser(appUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@NotNull @PathVariable("id") final Long id) {
        repository.deleteById(id);
    }
}
