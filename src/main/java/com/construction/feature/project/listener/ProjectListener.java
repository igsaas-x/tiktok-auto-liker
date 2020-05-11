package com.construction.feature.project.listener;

import com.construction.appconfiguration.utils.AutowiringHelper;
import com.construction.feature.project.repository.ProjectRepository;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PreUpdate;

@Component
public class ProjectListener {

    @Autowired
    private ProjectRepository repository;

    @PreUpdate
    private void doSth(AppUser appUser){
        AutowiringHelper.autowire(this, repository);
    }
}
