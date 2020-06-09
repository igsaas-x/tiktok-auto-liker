package com.construction.user.authentication.dto;

import com.construction.persistence.mapper.DtoMapper;
import com.construction.user.authentication.domain.AppUser;
import com.construction.user.authorization.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDataMapper implements DtoMapper<UserDto, AppUser> {

    final UserRoleRepository repository;
    final PasswordEncoder encoder;

    @Override
    public AppUser toEntity(UserDto dto) {
        var user = new AppUser().setUserName(dto.getUserName())
                .setEmail(dto.getEmail())
                .setMobile(dto.getMobile());
        if (dto.getRoleId() != null) {
            user.setRole(repository.findById(dto.getRoleId()).orElseThrow());
        }
        if (dto.getPassword() != null) {
            user.setPassword(encoder.encode(dto.getPassword()));
        }
        return user;
    }

    @Override
    public UserDto toDto(AppUser o) {
        return null;
    }
}
