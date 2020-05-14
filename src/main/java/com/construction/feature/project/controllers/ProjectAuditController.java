package com.construction.feature.project.controllers;

import com.construction.feature.project.domain.ProjectAudit;
import com.construction.feature.project.services.ProjectAuditService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 服务类
 *
 * @author chanheng hehehe
 * @since 1.0.0
 */
@RestController
@CrossOrigin
@RequestMapping("/projectAudit")
public class ProjectAuditController {

    final
    ProjectAuditService projectAuditService;

    public ProjectAuditController(ProjectAuditService projectAuditService) {
        this.projectAuditService = projectAuditService;
    }

    /**
     * 根据ProjectAudit的字段,自动生成条件,字段的值为null不生成条件
     * http://localhost:8080/user/?id=1
     *
     * @param projectAudit 实体对象
     * @param pageable     分页/排序对象
     * @return 返回的是实体, 里面涵盖分页信息及状态码
     */
    @GetMapping
    ResponseEntity<Object> search(ProjectAudit projectAudit, Pageable pageable) {
        return projectAuditService.search(projectAudit, pageable);
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
