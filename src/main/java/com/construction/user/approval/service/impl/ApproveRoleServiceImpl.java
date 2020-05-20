package com.construction.user.approval.service.impl;

import com.construction.user.approval.dao.ApproveRoleRepository;
import com.construction.user.approval.domain.ApproveRole;
import com.construction.user.approval.dto.ApproveRoleDTO;
import com.construction.user.approval.mapper.ApproveRoleMapper;
import com.construction.user.approval.service.ApproveRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApproveRoleServiceImpl implements ApproveRoleService {

    private final ApproveRoleMapper mapper;
    private final ApproveRoleRepository repository;

    public ApproveRoleServiceImpl(ApproveRoleMapper mapper, ApproveRoleRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ApproveRoleDTO save(ApproveRoleDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void save(List<ApproveRoleDTO> dtos) {
        repository.saveAll(mapper.toEntityList(dtos));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ApproveRoleDTO> findById(Long id) {
        Optional<ApproveRole> entityOptional = repository.findById(id);
        return entityOptional.map(entity -> Optional.ofNullable(mapper.toDto(entity))).orElse(null);
    }

    @Override
    public List<ApproveRoleDTO> findAll() {
        return mapper.toDtoList((List<ApproveRole>) repository.findAll());
    }

    @Override
    public Page<ApproveRoleDTO> findAll(Pageable pageable) {
        Page<ApproveRole> entityPage = repository.findAll(pageable);
        List<ApproveRoleDTO> dtos = mapper.toDtoList(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public ApproveRoleDTO update(Long id, ApproveRoleDTO dto) {
        Optional<ApproveRoleDTO> optionalDto = findById(id);
        if (optionalDto.isPresent()) {
            return save(dto);
        }
        return null;
    }
}