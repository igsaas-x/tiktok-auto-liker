package com.construction.user.approval.dto;

import com.construction.user.authentication.domain.AppUser;

import java.util.List;

public class ApproveRoleDTO {
    private String name;
    private List<AppUser> users;

    public ApproveRoleDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setUsers(java.util.List<com.construction.user.authentication.domain.AppUser> users) {
        this.users = users;
    }

    public java.util.List<com.construction.user.authentication.domain.AppUser> getUsers() {
        return this.users;
    }
}