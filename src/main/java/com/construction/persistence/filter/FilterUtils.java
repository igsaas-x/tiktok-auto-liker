package com.construction.persistence.filter;

import com.construction.organization.payment.domain.PaymentEntryStatus;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class FilterUtils {

    @PersistenceContext
    private EntityManager entityManager;

    public void enableNoAccessFilter() {
        var session = entityManager.unwrap(Session.class);
        session.enableFilter("noAccessFilter");
    }

    public void enableReadWriteFilter(final Long id, final String assignFor) {
        var session = entityManager.unwrap(Session.class);
        session.enableFilter("readWriteFilter")
                .setParameter("id", id)
                .setParameter("assignFor", assignFor);
    }

    public void enableReadFilter(final Long id) {
        var session = entityManager.unwrap(Session.class);
        session.enableFilter("readFilter").setParameter("id", id);
    }

    public void enablePendingRequestFilter(final PaymentEntryStatus status) {
        var session = entityManager.unwrap(Session.class);
        session.enableFilter("pendingRequestFilter").setParameter("status", status.toString());
    }
}
