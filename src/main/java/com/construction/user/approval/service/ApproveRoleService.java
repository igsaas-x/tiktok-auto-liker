package com.construction.user.approval.service;

import com.construction.user.approval.dto.ApproveRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApproveRoleService {
    ApproveRoleDTO save(ApproveRoleDTO dto);

    void save(List<ApproveRoleDTO> dtos);

    void deleteById(Long id);

    Optional<ApproveRoleDTO> findById(Long id);

    List<ApproveRoleDTO> findAll();

    Page<ApproveRoleDTO> findAll(Pageable pageable);

    ApproveRoleDTO update(Long id, ApproveRoleDTO dto);
}