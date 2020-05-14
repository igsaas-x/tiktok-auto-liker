package com.construction.basic.keyvalue.services;

import com.construction.persistence.utils.SFWhere;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired ; 
import com.construction.basic.keyvalue.repositories.KeyValueRepository; 
import com.construction.basic.keyvalue.domain.KeyValue;

/**
 * 服务类
 * @author 刘前进 xindong888@163.com  www.xjke.com
 * @since 1.0.0
 */
@Service
public class KeyValueService {
    @Autowired
    KeyValueRepository keyValueRepository;

    public ResponseEntity<Object> search(KeyValue keyValue, Pageable pageable) {
        Page<KeyValue> all = keyValueRepository.findAll(SFWhere.and(keyValue)
                //.equal(user.getId() > 0, "id", user.getId())
                //.in(true, "userName", longs)
                //.equal(!字段值的判断.equals("") && 字段值的判断 != null, "字段名称", 字段值)
                //.like(字段值的判断 != null, "字段名称", "%" + 字段值 + "%")
                //....
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
