package com.construction.feature.project.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import com.construction.feature.project.domain.Project;
import com.construction.feature.project.services.ProjectService;
/**
 * 服务类
 * @author chanheng hehehe
 * @since 1.0.0
 */
@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {

    final
    ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * 根据Project的字段,自动生成条件,字段的值为null不生成条件
     * http://localhost:8080/user/?id=1
     * @param project 实体对象
     * @param pageable 分页/排序对象
     * @return 返回的是实体,里面涵盖分页信息及状态码
     */
     @GetMapping
     ResponseEntity<Object> search(Project project, Pageable pageable) {
             return projectService.search(project, pageable);
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
