package com.construction.user.authorization.service;

import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.user.authorization.domain.Permission;
import com.construction.user.authorization.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PermissionService {

    private final PermissionRepository repository;

    @Cacheable("permissions")
    public List<Permission> getAll() {
        return repository.findAll();
    }

    @Cacheable(value = "permission", key = "#id")
    public Permission getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Permission.class, id));
    }

    public List<Permission> getAllByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }
}
