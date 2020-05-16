package com.construction.user.authorization.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.construction.user.authorization.domain.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long>{

}