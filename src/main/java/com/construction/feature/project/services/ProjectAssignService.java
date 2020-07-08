package com.construction.feature.project.services;

import com.construction.feature.project.domain.Project;
import com.construction.feature.project.domain.ProjectAssign;
import com.construction.feature.project.repositories.ProjectAssignRepository;
import com.construction.feature.project.repositories.ProjectRepository;
import com.construction.persistence.dto.AssignedDto;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.user.authentication.domain.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectAssignService {

    private final ProjectAssignRepository repository;
    private final ProjectRepository projectRepository;

    public List<AssignedDto> getAssignedUser(final Long id) {
        var project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Project.class, id));
        return repository.findAllByProject(project)
                .stream()
                .map(AssignedDto::fromEntity)
                .distinct()
                .collect(Collectors.toList());
    }
}
