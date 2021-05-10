package com.construction.persistence.utils;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class MyIdGenerator implements IdentifierGenerator, Configurable {

    private String prefix;
    private String key;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        var query = String.format("select s_value from sequence_helper where s_key = '%s'", key);
        var max = session.createQuery(query, Long.class).getSingleResult();
        return prefix + (max + 1);
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
        key = properties.getProperty("key");
    }
}
