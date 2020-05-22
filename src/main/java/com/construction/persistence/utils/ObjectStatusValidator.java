package com.construction.persistence.utils;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.persistence.domain.AuditingEntity;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.user.authorization.domain.ActionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ObjectStatusValidator<T extends AuditingEntity> {

    @Autowired
    private ApplicationSecurityContext context;

    public void validateStatus(T t, ActionName actionName) {
        switch (actionName) {
            case VERIFY:
                if (!t.getStatus().equals(ObjectStatus.OPEN)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "project is not on open status");
                }
                break;
            case APPROVE:
                if (!t.getStatus().equals(ObjectStatus.VERIFIED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "project is not on verified status");
                }
                break;
            case UPDATE:
                if (t.getCreatedBy().equals(context.authenticatedUser())) {
                    if (t.getStatus().equals(ObjectStatus.VERIFIED) || t.getStatus().equals(ObjectStatus.APPROVED)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "your project has been verified or approved, please delete and re-create request");
                    }
                }
        }
    }

}
