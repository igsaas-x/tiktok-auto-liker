package com.construction.feature.task.service;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.Task;
import com.construction.feature.task.repository.BOQRepository;
import com.construction.persistence.dto.SidList;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BOQService {

    private final BOQRepository repository;
    private final EntityDataMapper dataMapper;
    private final TaskService taskService;
    private final TaskSubConstructAssignService subConstructAssignService;
    private final EntityManager em;

    public BOQ save(BOQ boq) {
        return repository.save(boq);
    }

    public void deleteById(Long id) {
        var boq = getById(id);
        repository.delete(boq);
    }

    public BOQ getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(BOQ.class, id));
    }

    @SuppressWarnings("unchecked")
    public List<BOQ> search(String code,
                            Long projectId,
                            Long houseId,
                            Long streetId,
                            Pageable pageable) {
        StringBuilder sql = new StringBuilder("select * from boq where 1=1 ");

        if (StringUtils.hasText(code)) {
            sql.append(" and code = '").append(code).append("'");
        }
        if (projectId != null) {
            sql.append(" and project_id = ").append(projectId);
        }
        if (houseId != null) {
            sql.append(" and house_id = ").append(houseId);
        }
        if (streetId != null) {
            sql.append(" and street_id = ").append(streetId);
        }
        if (pageable != null) {
            sql.append(" limit ").append(pageable.getPageSize());
            sql.append(" offset ").append(pageable.getOffset());
        }

        return em.createNativeQuery(sql.toString(), BOQ.class).getResultList();
    }

    public List<BOQ> findAll() {
        return repository.findAll();
    }

    public Page<BOQ> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public BOQ updateById(Long id, BOQ boq) {
        var target = getById(id);
        target = dataMapper.mapObject(boq, target, BOQ.class);
        return repository.save(target);
    }

    public BOQ addTask(Long id, List<Task> tasks) {
        var boq = getById(id);
        boq.getTasks().addAll(tasks);
        return repository.save(boq);
    }

    public boolean removeTask(Long taskId) {
        taskService.deleteById(taskId);
        return true;
    }

    public boolean verifyAllTask(Long id) {
        var boq = getById(id);
        taskService.getByBoq(boq).forEach(taskService::verify);
        return true;
    }

    public boolean approveAllTask(Long id) {
        var boq = getById(id);
        taskService.getByBoq(boq).forEach(taskService::approve);
        return true;
    }

    public boolean assign(Long boqId, SidList sids) {
        taskService.getByBoqId(boqId).forEach(task -> {
            try {
                subConstructAssignService.assign(task.getId(), sids.getSids());
            } catch (Exception e) {
                log.error("cannot add sub-constructor to task id:{}", task.getId());
            }
        });
        return true;
    }

    public boolean unAssign(Long boqId, SidList sids) {
        taskService.getByBoqId(boqId).forEach(task -> {
            try {
                subConstructAssignService.unAssign(task.getId(), sids.getSids());
            } catch (Exception e) {
                log.error("cannot unassign task id:{}", task.getId());
            }
        });
        return true;
    }
}