package com.construction.feature.house.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import com.construction.feature.house.domain.House;
import com.construction.feature.house.services.HouseService;
/**
 * 服务类
 * @author 申畅恒
 * @since 1.0.0
 */
@RestController
@CrossOrigin
@RequestMapping("/house")
public class HouseController {

    final
    HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    /**
     * 根据House的字段,自动生成条件,字段的值为null不生成条件
     * http://localhost:8080/user/?id=1
     * @param house 实体对象
     * @param pageable 分页/排序对象
     * @return 返回的是实体,里面涵盖分页信息及状态码
     */
     @GetMapping
     ResponseEntity<Object> search(House house, Pageable pageable) {
             return houseService.search(house, pageable);
     }
     @PostMapping
     void create() {
     }
     @PutMapping
     void update(){
     }
     @DeleteMapping("/")
     void delete(){
     }
}
