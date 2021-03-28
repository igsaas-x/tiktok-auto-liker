package com.construction.feature.task.dto.mapper;

import com.construction.feature.house.repository.HouseRepository;
import com.construction.feature.project.repositories.ProjectRepository;
import com.construction.feature.street.repository.StreetRepository;
import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.dto.BOQDto;
import com.construction.feature.task.repository.TaskTemplateRepository;
import com.construction.persistence.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BOQMapper extends DtoMapper<BOQ, BOQDto> {

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public BOQDto apply(BOQ boq) {
        final var boqDto = super.apply(boq);
        final var tasks = boq.getTasks();
        final var tasksDto = tasks.stream().map(taskMapper).collect(Collectors.toList());
        boqDto.setTasks(tasksDto);
        return boqDto;
    }

    @Override
    public BOQ toEntity(BOQDto dto) {
        final var boq = new BOQ()
                .setCode(dto.getCode())
                .setDetails(dto.getDetails());
        if (dto.getHouseId() != null) {
            final var house = houseRepository.findById(dto.getHouseId()).orElseThrow();
            boq.setHouse(house);
        }
        if (dto.getProjectId() != null) {
            final var project = projectRepository.findById(dto.getProjectId()).orElseThrow();
            boq.setProject(project);
        }
        if (dto.getStreetId() != null) {
            final var street = streetRepository.findById(dto.getStreetId()).orElseThrow();
            boq.setStreet(street);
        }
        if (dto.getTasks() != null) {
            final var tasks = dto.getTasks().stream()
                    .map(taskMapper::toEntity)
                    .collect(Collectors.toList());
            boq.setTasks(tasks);
        }
        return boq;
    }
}
