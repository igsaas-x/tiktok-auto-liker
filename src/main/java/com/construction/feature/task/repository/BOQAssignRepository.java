package com.construction.feature.task.repository;

import com.construction.feature.task.domain.BOQ;
import com.construction.feature.task.domain.BOQAssign;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BOQAssignRepository extends JpaRepository<BOQAssign, Long> {
    Optional<BOQAssign> findByBoqAndAppUser(BOQ boq, AppUser user);
}
