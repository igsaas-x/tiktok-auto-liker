package com.construction.organization.subconstructor.services;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired ; 
import com.construction.organization.subconstructor.repositories.SubConstructorRepository; 
import com.construction.organization.subconstructor.utility.SFWhere; 
import com.construction.organization.subconstructor.domain.SubConstructor;

/**
 * 服务类
 * @author chanheng seang
 * @since 1.0.0
 */
@Service
public class SubConstructorService {
    @Autowired
    SubConstructorRepository subConstructorRepository;
    /**
     * 根据SubConstructor的字段自动生成条件,字段值为null不生成条件
     * 如果是数值型的字段,前端不传入值,默认是0,例如ID的类型是Long,如果不传值,默认是0
     * 可以自己设置下SFWhere.and(subConstructor).equal(实体.getId()>0,"id",实体.getId()).build()
     * @param subConstructor     实体对象
     * @param pageable 分页对象
     * @return 返回分页\状态码
     */
    public ResponseEntity<Object> search(SubConstructor subConstructor, Pageable pageable) {
        Page<SubConstructor> all = subConstructorRepository.findAll(SFWhere.and(subConstructor)
                //.equal(user.getId() > 0, "id", user.getId())
                //.in(true, "userName", longs)
                //.equal(!字段值的判断.equals("") && 字段值的判断 != null, "字段名称", 字段值)
                //.like(字段值的判断 != null, "字段名称", "%" + 字段值 + "%")
                //....
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
