package com.construction.feature.house.service;

import com.construction.feature.house.domain.House;
import com.construction.feature.house.repository.HouseAssignRepository;
import com.construction.feature.house.repository.HouseRepository;
import com.construction.persistence.dto.AssignedDto;
import com.construction.persistence.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HouseAssignService {

    private final HouseAssignRepository repository;
    private final HouseRepository houseRepository;

    public List<AssignedDto> getAssignedUser(final Long id) {
        var house = houseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(House.class, id));
        return repository.findAllByHouse(house)
                .stream()
                .map(AssignedDto::fromEntity)
                .collect(Collectors.toList());
    }
}
