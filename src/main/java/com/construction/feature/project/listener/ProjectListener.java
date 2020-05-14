package com.construction.feature.project.listener;

import com.construction.appconfiguration.utils.AutowiringHelper;
import com.construction.feature.project.domain.Project;
import com.construction.feature.project.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectListener {

    @Autowired
    private ProjectRepository repository;

    private void doSth(Project project) {
        AutowiringHelper.autowire(this, repository);
    }
}
