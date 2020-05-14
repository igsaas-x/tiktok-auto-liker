package com.construction.organization.subconstructor.controllers;

import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.services.SubConstructorService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 服务类
 *
 * @author chanheng seang
 * @since 1.0.0
 */
@RestController
@CrossOrigin
@RequestMapping("/subConstructor")
public class SubConstructorController {

    final
    SubConstructorService subConstructorService;

    public SubConstructorController(SubConstructorService subConstructorService) {
        this.subConstructorService = subConstructorService;
    }

    /**
     * 根据SubConstructor的字段,自动生成条件,字段的值为null不生成条件
     * http://localhost:8080/user/?id=1
     *
     * @param subConstructor 实体对象
     * @param pageable       分页/排序对象
     * @return 返回的是实体, 里面涵盖分页信息及状态码
     */
    @GetMapping
    ResponseEntity<Object> search(SubConstructor subConstructor, Pageable pageable) {
        return subConstructorService.search(subConstructor, pageable);
    }

    @PostMapping
    void create() {
    }

    @PutMapping
    void update() {
    }

    @DeleteMapping("/")
    void delete() {
    }
}
