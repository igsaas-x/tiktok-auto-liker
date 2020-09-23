package com.construction.persistence.service;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

@Service
@SuppressWarnings("unchecked")
public class EntityDataMapper {

    private static final Set<String> SKIP = Set.of(
            "id",
            "createdBy",
            "createdAt",
            "updatedBy",
            "updatedAt",
            "verifiedBy",
            "verifiedAt",
            "approvedBy",
            "approvedAt",
            "status");

    @PersistenceContext
    private EntityManager entityManager;

    public <T> T mapObject(final T sourceObject, final T targetObject, Class<T> clazz) {
        final var targetWrapper = new BeanWrapperImpl(targetObject);
        final var sourceWrapper = new BeanWrapperImpl(sourceObject);
        final var en = entityManager.getMetamodel().entity(clazz);
        en.getAttributes().forEach(attribute -> {
            if (!SKIP.contains(attribute.getName()))
                targetWrapper.setPropertyValue(attribute.getName(), sourceWrapper.getPropertyValue(attribute.getName()));
        });
        return (T) targetWrapper.getWrappedInstance();
    }
}
