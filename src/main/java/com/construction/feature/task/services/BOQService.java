package com.construction.feature.task.services;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.repositories.BOQRepository;
import com.construction.persistence.utils.SFWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BOQService {
    @Autowired
    BOQRepository bOQRepository;

    public ResponseEntity<Object> search(BOQ bOQ, Pageable pageable) {
        Page<BOQ> all = bOQRepository.findAll(SFWhere.and(bOQ)
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
