package com.construction.persistence.repository;

import com.construction.persistence.domain.SequenceHelper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SequenceHelperRepository extends JpaRepository<SequenceHelper, String> {
    Optional<SequenceHelper> findByKey(String key);
}
