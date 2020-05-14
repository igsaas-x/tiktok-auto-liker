package com.construction.feature.project.services;

import com.construction.feature.project.domain.Project;
import com.construction.feature.project.repositories.ProjectRepository;
import com.construction.feature.project.utility.SFWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 服务类
 *
 * @author chanheng hehehe
 * @since 1.0.0
 */
@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    /**
     * 根据Project的字段自动生成条件,字段值为null不生成条件
     * 如果是数值型的字段,前端不传入值,默认是0,例如ID的类型是Long,如果不传值,默认是0
     * 可以自己设置下SFWhere.and(project).equal(实体.getId()>0,"id",实体.getId()).build()
     *
     * @param project  实体对象
     * @param pageable 分页对象
     * @return 返回分页\状态码
     */
    public ResponseEntity<Object> search(Project project, Pageable pageable) {
        Page<Project> all = projectRepository.findAll(SFWhere.and(project)
                //.equal(user.getId() > 0, "id", user.getId())
                //.in(true, "userName", longs)
                //.equal(!字段值的判断.equals("") && 字段值的判断 != null, "字段名称", 字段值)
                //.like(字段值的判断 != null, "字段名称", "%" + 字段值 + "%")
                //....
                .build(), pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
