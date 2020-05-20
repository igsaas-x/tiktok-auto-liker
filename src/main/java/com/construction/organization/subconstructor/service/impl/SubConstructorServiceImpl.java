package com.construction.organization.subconstructor.service.impl;

import com.construction.organization.subconstructor.dao.SubConstructorRepository;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.dto.SubConstructorDTO;
import com.construction.organization.subconstructor.mapper.SubConstructorMapper;
import com.construction.organization.subconstructor.service.SubConstructorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubConstructorServiceImpl implements SubConstructorService {
    private final SubConstructorMapper mapper;
    private final SubConstructorRepository repository;

    public SubConstructorServiceImpl(SubConstructorMapper mapper, SubConstructorRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public SubConstructorDTO save(SubConstructorDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void save(List<SubConstructorDTO> dtos) {
        repository.saveAll(mapper.toEntityList(dtos));
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<SubConstructorDTO> findById(String id) {
        Optional<SubConstructor> entityOptional = repository.findById(id);
        return entityOptional.map(entity -> Optional.ofNullable(mapper.toDto(entity))).orElse(null);
    }

    @Override
    public List<SubConstructorDTO> findAll() {
        return mapper.toDtoList((List<SubConstructor>) repository.findAll());
    }

    @Override
    public Page<SubConstructorDTO> findAll(Pageable pageable) {
        Page<SubConstructor> entityPage = repository.findAll(pageable);
        List<SubConstructorDTO> dtos = mapper.toDtoList(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public SubConstructorDTO updateById(SubConstructorDTO dto) {
        Optional<SubConstructorDTO> optionalDto = findById(dto.getCustomerId());
        if (optionalDto.isPresent()) {
            return save(dto);
        }
        return null;
    }
}