package com.construction.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class SequenceHelperService {

    public static final String SUB_CONSTRUCTOR_KEY = "SUB_CONSTRUCTOR";
    private static final String SQL = String.format(
            "replace into sequence_helper(s_key,s_value) values('%s', 1)",
            SUB_CONSTRUCTOR_KEY);

    @Autowired
    private TransactionTemplate transactionTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void insertDefault() {
        transactionTemplate.execute(transactionStatus -> {
            entityManager.createNativeQuery(SQL).executeUpdate();
            transactionStatus.flush();
            return null;
        });
    }
}
