package com.construction.feature.street.service;

import com.construction.feature.street.domain.Street;
import com.construction.feature.street.repository.StreetAssignRepository;
import com.construction.feature.street.repository.StreetRepository;
import com.construction.persistence.dto.AssignedDto;
import com.construction.persistence.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreetAssignService {

    private final StreetAssignRepository repository;
    private final StreetRepository streetRepository;

    public List<AssignedDto> getAssignedUser(final Long id) {
        var street = streetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Street.class, id));
        return repository.findAllByStreet(street)
                .stream()
                .map(AssignedDto::fromEntity)
                .collect(Collectors.toList());
    }
}
