package com.construction.organization.subconstructor.services;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.repositories.SubConstructorRepository;
import com.construction.persistence.utils.SFWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubConstructorService {
    @Autowired
    SubConstructorRepository subConstructorRepository;

    public ResponseEntity<Object> search(SubConstructor subConstructor, Pageable pageable) {
        Page<SubConstructor> all = subConstructorRepository.findAll(SFWhere.and(subConstructor)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
