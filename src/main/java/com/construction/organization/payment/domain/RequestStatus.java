package com.construction.organization.payment.domain;

import com.construction.user.authentication.domain.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Setter
@Getter
@Accessors(chain = true)
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestStatus {

    boolean done;

    LocalDateTime doneAt;

    @ManyToOne
    @JsonIgnore
    private AppUser doneBy;
}
