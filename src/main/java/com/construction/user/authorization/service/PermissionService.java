package com.construction.user.authorization.service;

import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.user.authorization.domain.Permission;
import com.construction.user.authorization.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository repository;

    @Cacheable("permissions")
    public List<Permission> getAll() {
        return repository.findAll();
    }

    @Cacheable(value = "permission", key = "#id")
    public Permission getById(Long id) {
        log.info("get permission has been called");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Permission.class, id));
    }

    public List<Permission> getAllByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }
}
