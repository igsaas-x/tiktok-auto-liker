package com.construction.feature.task.service.impl;

import com.construction.feature.task.dao.BOQRepository;
import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.dto.BOQDTO;
import com.construction.feature.task.mapper.BOQMapper;
import com.construction.feature.task.service.BOQService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BOQServiceImpl implements BOQService {
    private final BOQMapper mapper;
    private final BOQRepository repository;

    public BOQServiceImpl(BOQMapper mapper, BOQRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public BOQDTO save(BOQDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void save(List<BOQDTO> dtos) {
        repository.saveAll(mapper.toEntityList(dtos));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<BOQDTO> findById(Long id) {
        Optional<BOQ> entityOptional = repository.findById(id);
        return entityOptional.map(entity -> Optional.ofNullable(mapper.toDto(entity))).orElse(null);
    }

    @Override
    public List<BOQDTO> findAll() {
        return mapper.toDtoList((List<BOQ>) repository.findAll());
    }

    @Override
    public Page<BOQDTO> findAll(Pageable pageable) {
        Page<BOQ> entityPage = repository.findAll(pageable);
        List<BOQDTO> dtos = mapper.toDtoList(entityPage.getContent());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public BOQDTO updateById(Long id) {
        Optional<BOQDTO> optionalDto = findById(id);
        return optionalDto.map(this::save).orElse(null);
    }
}