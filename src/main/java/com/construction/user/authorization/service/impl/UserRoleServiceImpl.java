package com.construction.user.authorization.service.impl;

import com.construction.user.authorization.dao.UserRoleRepository;
import com.construction.user.authorization.domain.UserRole;
import com.construction.user.authorization.dto.UserRoleDTO;
import com.construction.user.authorization.mapper.UserRoleMapper;
import com.construction.user.authorization.service.UserRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleMapper mapper;
    private final UserRoleRepository repository;

    public UserRoleServiceImpl(UserRoleMapper mapper, UserRoleRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public UserRoleDTO save(UserRoleDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void save(List<UserRoleDTO> dtos) {
        repository.saveAll(mapper.toEntityList(dtos));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<UserRoleDTO> findById(Long id) {
        Optional<UserRole> entityOptional = repository.findById(id);
        return entityOptional.map(entity -> Optional.ofNullable(mapper.toDto(entity))).orElse(null);
    }

    @Override
    public List<UserRoleDTO> findAll() {
        return mapper.toDtoList((List<UserRole>) repository.findAll());
    }

    @Override
    public Page<UserRoleDTO> findAll(Pageable pageable) {
        Page<UserRole> entityPage = repository.findAll(pageable);
        List<UserRoleDTO> dtos = mapper.toDtoList(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

/*    @Override
    public UserRoleDTO updateById(UserRoleDTO dto) {
        Optional<UserRoleDTO> optionalDto = findById(dto.getName());
        if (optionalDto.isPresent()) {
            return save(dto);
        }
        return null;
    }*/
}