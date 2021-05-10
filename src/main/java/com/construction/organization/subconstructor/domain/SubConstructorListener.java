package com.construction.organization.subconstructor.domain;

import com.construction.appconfiguration.utils.AutowiringHelper;
import com.construction.persistence.repository.SequenceHelperRepository;
import com.construction.persistence.service.SequenceHelperService;
import com.construction.persistence.utils.MyStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.PrePersist;

public class SubConstructorListener {

    @Autowired
    private SequenceHelperRepository helperRepository;

    @PrePersist
    @Transactional
    private void prePersist(final SubConstructor subConstructor) {
        if (!StringUtils.hasText(subConstructor.getEngFullName())) {
            subConstructor.setEngFullName(subConstructor.getFirstName() + " " + subConstructor.getLastName());
        }
        AutowiringHelper.autowire(this, helperRepository);
        var sequence = helperRepository.findByKey(SequenceHelperService.SUB_CONSTRUCTOR_KEY).orElseThrow();
        subConstructor.setExternalId("EN" + MyStringUtils.leftPad("" + sequence.getValue(), 5));
        sequence.increase();
        helperRepository.save(sequence);
    }
}
