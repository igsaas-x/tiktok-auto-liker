package com.construction.user.authentication.domain;

import com.construction.persistence.domain.VersionEntity;
import com.construction.user.authorization.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static com.construction.user.authentication.service.AppUserService.ALL_PERMISSION;

@Entity
@Getter
@Setter
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
@Accessors(chain = true)
@Where(clause = "status <> 'DELETED'")
@SQLDelete(sql = "update user set status = 'DELETED' where id = ? and version = ?")
@JsonIgnoreProperties(value = "password", allowSetters = true)
public class AppUser extends VersionEntity {

    @NotNull
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Email
    private String email;

    private String mobile;

    @NotNull
    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn
    private UserRole role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    public boolean hasPermissionTo(final String permissionName) {
        if (role == null) return false;
        return role.getPermissions().stream().anyMatch(permission ->
                permission.getCodeName().equals(ALL_PERMISSION) ||
                        permission.getCodeName().equals(permissionName)
        );
    }
}
