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

    public void enableAssignedObjectFilter(final Long id) {
        var session = entityManager.unwrap(Session.class);
        session.enableFilter("assignedObjectFilter").setParameter("id", id);
    }

    public void enableMyObjectFilter(final Long id) {
        var session = entityManager.unwrap(Session.class);
        session.enableFilter("myObjectFilter").setParameter("id", id);
    }

    public void enableReadableObjectFilter(final Long id) {
        var session = entityManager.unwrap(Session.class);
        session.enableFilter("readableObjectFilter").setParameter("id", id);
    }

    public void enablePendingRequestFilter(final PaymentEntryStatus status) {
        var session = entityManager.unwrap(Session.class);
        session.enableFilter("pendingRequestFilter").setParameter("status", status.toString());
    }
}
