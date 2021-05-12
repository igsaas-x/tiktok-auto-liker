package com.construction.organization.subconstructor.services;

import com.construction.appconfiguration.utils.ApplicationSecurityContext;
import com.construction.feature.FilterType;
import com.construction.organization.subconstructor.data.SubConstructorData;
import com.construction.organization.subconstructor.domain.SubConstructor;
import com.construction.organization.subconstructor.repository.SubConstructorRepository;
import com.construction.persistence.domain.ObjectStatus;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.persistence.utils.EntityValidator;
import com.construction.user.authorization.domain.ActionName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubConstructorService {

    private final SubConstructorRepository repository;
    private final EntityDataMapper dataMapper;
    private final EntityValidator<SubConstructor> validator;
    private final ApplicationSecurityContext context;
    private final EntityManager em;

    public SubConstructor create(final SubConstructor subConstructor) {
        return repository.save(subConstructor);
    }

    public SubConstructor getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SubConstructor.class, id));
    }

    @SuppressWarnings("unchecked")
    public List<SubConstructor> search(ObjectStatus status, String name, Pageable pageable) {

        StringBuilder sql = new StringBuilder("select * from sub_constructor where 1=1 ");

        if (StringUtils.hasText(name)) {
            sql.append(" and eng_full_name like '%").append(name).append("%'");
        }
        if (status != null) {
            sql.append(" and status = '").append(status).append("'");
        }
        if (pageable != null) {
            sql.append(" limit ").append(pageable.getPageSize());
            sql.append(" offset ").append(pageable.getOffset());
        }

        return em.createNativeQuery(sql.toString(), SubConstructor.class).getResultList();
    }

    public List<SubConstructor> getAll() {
        return repository.findAll();
    }

    public Page<SubConstructor> getAll(Pageable pageable, FilterType filterType) {
        switch (filterType) {
            case OWNED:
                return repository.findAllByCreatedById(context.authenticatedUser().getId(), pageable);
            case PENDING_FOR_VERIFY:
                return repository.findAllByStatus(ObjectStatus.OPEN, pageable);
            case PENDING_FOR_APPROVE:
                return repository.findAllByStatus(ObjectStatus.VERIFIED, pageable);
            case ALL:
                return repository.findAll(pageable);
            default:
                return null;
        }
    }

    public void delete(Long id) {
        var target = getById(id);
        repository.delete(target);
    }

    public List<SubConstructor> getPendingForVerify() {
        return repository.findAllByStatus(ObjectStatus.OPEN);
    }

    public List<SubConstructor> getPendingForApprove() {
        return repository.findAllByStatus(ObjectStatus.VERIFIED);
    }

    public SubConstructor update(Long id, SubConstructor source) {
        var target = getById(id);
        validator.validateStatus(target, ActionName.UPDATE);
        target = dataMapper.mapObject(source, target, SubConstructor.class);
        return repository.save(target);
    }

    public SubConstructor verify(Long id) {
        var project = getById(id);
        validator.validateStatus(project, ActionName.VERIFY);
        project.setStatus(ObjectStatus.VERIFIED);
        project.setVerifiedBy(context.authenticatedUser());
        project.setVerifiedAt(LocalDateTime.now());
        return repository.save(project);
    }

    public SubConstructor approve(Long id) {
        var project = getById(id);
        validator.validateStatus(project, ActionName.APPROVE);
        project.setStatus(ObjectStatus.APPROVED);
        project.setApprovedBy(context.authenticatedUser());
        project.setApprovedAt(LocalDateTime.now());
        return repository.save(project);
    }

    public SubConstructor addFingerPrint(SubConstructorData subConstructorData) {
        SubConstructor subConstructor = repository.findById(subConstructorData.getId())
                .orElseThrow(() -> new ResourceNotFoundException(SubConstructor.class, subConstructorData.getId()));
        subConstructor.setBase64(subConstructorData.getBase64());
        return repository.save(subConstructor);
    }
}
