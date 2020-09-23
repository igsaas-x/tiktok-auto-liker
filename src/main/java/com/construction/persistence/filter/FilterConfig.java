package com.construction.persistence.filter;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.organization.payment.domain.PaymentEntryStatus;
import com.construction.user.authorization.domain.ActionName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor
public class FilterConfig {

    private final ApplicationSecurityContext context;
    private final FilterUtils filterUtils;

    public void configureFilter(@NotNull final ActionName action, @NotNull final String entityName) {
        final var user = context.authenticatedUser();
        if (user == null) {
            filterUtils.enableNoAccessFilter();
        } else if (context.hasPermissionTo(action.name() + "_" + entityName.toUpperCase())) {
            return;
        } else {
            switch (action) {
                case READ:
                    filterUtils.enableReadFilter(user.getId());
                    break;
                case UPDATE:
                case DELETE:
                    filterUtils.enableReadWriteFilter(user.getId(), "READ_WRITE");
                    break;
                case VERIFY:
                case APPROVE:
                    filterUtils.enableReadWriteFilter(user.getId(), action.name());
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user has no permission to: " + action.name());
            }
        }
    }

    public void enableChildFilter(final PaymentEntryStatus status) {
        filterUtils.enablePendingRequestFilter(status);
    }
}
