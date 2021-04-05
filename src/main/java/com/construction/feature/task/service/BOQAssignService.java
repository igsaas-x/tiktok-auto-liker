package com.construction.feature.task.service;

import com.construction.feature.task.domain.BOQAssign;
import com.construction.feature.task.repository.BOQAssignRepository;
import com.construction.organization.subconstructor.domain.SubConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BOQAssignService {

    private final BOQAssignRepository repository;

    public List<SubConstructor> getAssignedSubConstructor(final Long boqId) {
        return repository.findByBoqId(boqId)
                .stream()
                .map(BOQAssign::getSubConstructor)
                .collect(Collectors.toList());
    }
}
