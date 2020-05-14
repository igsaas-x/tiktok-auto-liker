package com.construction.feature.task.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.services.TaskService;
/**
 * 服务类
 * @author heng
 * @since 1.0.0
 */
@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

    final
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * 根据Task的字段,自动生成条件,字段的值为null不生成条件
     * http://localhost:8080/user/?id=1
     * @param task 实体对象
     * @param pageable 分页/排序对象
     * @return 返回的是实体,里面涵盖分页信息及状态码
     */
     @GetMapping
     ResponseEntity<Object> search(Task task, Pageable pageable) {
             return taskService.search(task, pageable);
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
