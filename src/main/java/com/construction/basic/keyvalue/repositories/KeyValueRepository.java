package com.construction.basic.keyvalue.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.construction.basic.keyvalue.domain.KeyValue;

@Repository
public interface KeyValueRepository extends JpaRepository<KeyValue,Long>,JpaSpecificationExecutor<KeyValue>{

}