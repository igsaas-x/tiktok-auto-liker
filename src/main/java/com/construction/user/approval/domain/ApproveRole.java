package com.construction.user.approval.domain;

import com.construction.persistence.domain.VersionEntity;
import com.construction.user.authentication.domain.AppUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class ApproveRole extends VersionEntity {

    private String name;

    @OneToMany
    @JoinTable
    private List<AppUser> users;
}
