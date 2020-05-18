package com.construction.user.authorization.service;

import com.construction.user.authorization.dto.UserRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRoleService {
    UserRoleDTO save(UserRoleDTO dto);

    void save(List<UserRoleDTO> dtos);

    void deleteById(Long id);

    Optional<UserRoleDTO> findById(Long id);

    List<UserRoleDTO> findAll();

    Page<UserRoleDTO> findAll(Pageable pageable);

//    UserRoleDTO updateById(UserRoleDTO dto);
}