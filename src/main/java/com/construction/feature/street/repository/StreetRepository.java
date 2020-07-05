package com.construction.feature.street.repository;

import com.construction.feature.street.domain.Street;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {

    Page<Street> findAllByCreatedBy(final AppUser appUser, final Pageable pageable);

    @Query(value = "select s.* from street s, street_assign sa " +
            "where s.id =sa.street_id and sa.app_user_id = :userId and s.status = 'OPEN' AND sa.assign_for = 'VERIFY'", nativeQuery = true)
    Page<Street> findPendingForVerify(Long userId, Pageable pageable);

    @Query(value = "select s.* from street s, street_assign sa " +
            "where s.id =sa.street_id and sa.app_user_id = :userId and s.status = 'VERIFIED' AND sa.assign_for = 'APPROVE'", nativeQuery = true)
    Page<Street> findPendingForApprove(Long userId, Pageable pageable);

    @Query(value = "select s.* from street s, street_assign sa " +
            "where s.id =sa.street_id and sa.app_user_id = :userId", nativeQuery = true)
    Page<Street> findAssignedStreet(Long userId, Pageable pageable);
}