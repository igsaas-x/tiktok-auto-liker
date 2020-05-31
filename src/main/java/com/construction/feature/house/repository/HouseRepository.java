package com.construction.feature.house.repository;

import com.construction.feature.house.domain.House;
import com.construction.user.authentication.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findAllByCreatedBy(final AppUser user);

    @Query(value = "select h.* from house h, house_assign ha " +
            "where ha.assign_for = 'VERIFY' and h.id = ha.house_id and ha.app_user_id = :userId and h.status = 'OPEN'", nativeQuery = true)
    Page<House> findPendingForVerify(final Long userId, Pageable pageable);

    @Query(value = "select h.* from house h, house_assign ha " +
            "where ha.assign_for = 'APPROVE' and h.id = ha.house_id and ha.app_user_id = :userId and h.status = 'VERIFIED'", nativeQuery = true)
    Page<House> findPendingForApprove(final Long userId, Pageable pageable);
}