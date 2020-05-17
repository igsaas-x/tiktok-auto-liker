package com.construction.persistence.filter;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilterConfig {

    @Autowired
    private ApplicationSecurityContext context;
    @Autowired
    private FilterUtils filterUtils;

    public void configureFilter(String action, String entityName) {
        entityName = entityName.toUpperCase();
        action = action.toUpperCase();
        var user = context.authenticatedUser();
        if (user == null) {
            filterUtils.enableNoAccessFilter();
        } else if (user.hasPermissionTo(action + "_ALL_" + entityName)) {
            return;
        } else if (user.hasPermissionTo(action + "_ASSIGNED_" + entityName)) {
            filterUtils.enableAssignedObjectFilter(user.getId());
        } else {
            filterUtils.enableMyObjectFilter(user.getId());
        }
    }
}
