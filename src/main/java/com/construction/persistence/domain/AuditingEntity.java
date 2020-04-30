package com.construction.persistence.domain;

import com.construction.graphql.annotation.GQLIgnoreGenerate;
import com.construction.user.authentication.domain.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity extends ExtendedEntity {

    @JsonIgnore
    @CreatedBy
    @ManyToOne
    @GQLIgnoreGenerate
    @JoinColumn(name = "create_by_user_id")
    private AppUser createdBy;

    @JsonIgnore
    @LastModifiedBy
    @ManyToOne
    @GQLIgnoreGenerate
    @JoinColumn(name = "update_by_user_id")
    private AppUser updatedBy;

    @CreatedDate
    @GQLIgnoreGenerate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @GQLIgnoreGenerate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
