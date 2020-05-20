package com.construction.user.approval.dao;

import com.construction.user.approval.domain.ApproveRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproveRoleRepository extends JpaRepository<ApproveRole, Long> {
}