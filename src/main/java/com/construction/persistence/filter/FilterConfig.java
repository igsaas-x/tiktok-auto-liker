package com.construction.persistence.filter;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.user.authorization.domain.ActionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilterConfig {

    @Autowired
    private ApplicationSecurityContext context;
    @Autowired
    private FilterUtils filterUtils;

    public void configureFilter(ActionName action, String entityName) {
        entityName = entityName.toUpperCase();
        var user = context.authenticatedUser();
        if (user == null) {
            filterUtils.enableNoAccessFilter();
        } else if (user.hasPermissionTo(action.name() + "_ALL_" + entityName)) {
            return;
        } else if (!action.equals(ActionName.READ)) {
            if (user.hasPermissionTo(action.name() + "_ASSIGNED_" + entityName)) {
                filterUtils.enableAssignedObjectFilter(user.getId());
            }else {
                filterUtils.enableMyObjectFilter(user.getId());
            }
        } else {
            filterUtils.enableReadableObjectFilter(user.getId());
        }
    }
}
