package com.construction.user.authentication.controller;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authentication.dto.UserDataMapper;
import com.construction.user.authentication.dto.UserDto;
import com.construction.user.authentication.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService service;
    private final UserDataMapper mapper;
    private final ApplicationSecurityContext context;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_USER')")
    public List<AppUser> getAllUser() {
        return service.getAll();
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping("/by-role/{id}")
    public List<AppUser> getByRole(@PathVariable Long id) {
        return service.getByRole(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_USER')")
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
        return service.changePassword(context.authenticatedUser(), oldPassword, newPassword);
    }

    @PutMapping("/{id}")
    public AppUser updateUser(@NotNull @PathVariable final Long id, @RequestBody final UserDto dto) {
        return service.updateUser(id, mapper.toEntity(dto));
    }

    @PutMapping("/{id}/role/{roleId}")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public AppUser assignRole(@PathVariable Long id, @PathVariable Long roleId) {
        return service.assignRole(id, roleId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_USER')")
    public AppUser createUser(@NotNull @RequestBody final UserDto dto) {
        return service.createUser(mapper.toEntity(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public boolean deleteUser(@NotNull @PathVariable("id") final Long id) {
        if (id.equals(context.authenticatedUser().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot delete current user");
        }
        if (id == 1L) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot delete admin user");
        }
        service.deleteById(id);
        return true;
    }
}
