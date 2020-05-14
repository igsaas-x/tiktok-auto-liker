package com.construction.feature.house.services;

import com.construction.feature.house.domain.House;
import com.construction.feature.house.repositories.HouseRepository;
import com.construction.feature.house.utility.SFWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 服务类
 *
 * @author 申畅恒
 * @since 1.0.0
 */
@Service
public class HouseService {
    @Autowired
    HouseRepository houseRepository;

    /**
     * 根据House的字段自动生成条件,字段值为null不生成条件
     * 如果是数值型的字段,前端不传入值,默认是0,例如ID的类型是Long,如果不传值,默认是0
     * 可以自己设置下SFWhere.and(house).equal(实体.getId()>0,"id",实体.getId()).build()
     *
     * @param house    实体对象
     * @param pageable 分页对象
     * @return 返回分页\状态码
     */
    public ResponseEntity<Object> search(House house, Pageable pageable) {
        Page<House> all = houseRepository.findAll(SFWhere.and(house)
                //.equal(user.getId() > 0, "id", user.getId())
                //.in(true, "userName", longs)
                //.equal(!字段值的判断.equals("") && 字段值的判断 != null, "字段名称", 字段值)
                //.like(字段值的判断 != null, "字段名称", "%" + 字段值 + "%")
                //....
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
