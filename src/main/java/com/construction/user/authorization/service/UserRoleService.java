package com.construction.user.authorization.service;

import com.construction.persistence.service.EntityDataMapper;
import com.construction.user.authorization.domain.UserRole;
import com.construction.user.authorization.dto.RoleDto;
import com.construction.user.authorization.dto.RoleMapper;
import com.construction.user.authorization.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserRoleService {

    @Autowired
    private UserRoleRepository repository;
    @Autowired
    private EntityDataMapper dataMapper;
    @Autowired
    private RoleMapper roleMapper;

    public UserRole save(RoleDto roleDto) {
        var role = roleMapper.toEntity(roleDto);
        return repository.save(role);
    }

    public void save(List<UserRole> dtos) {
        repository.saveAll(dtos);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public UserRole findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<UserRole> findAll() {
        return repository.findAll();
    }

    public Page<UserRole> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public UserRole updateById(Long id, RoleDto roleDto) {
        var target = repository.findById(id).orElseThrow();
        var role = roleMapper.toEntity(roleDto);
        target = dataMapper.mapObject(role, target, UserRole.class);
        return repository.save(target);
    }
}