package com.actio.actio_api.repository;

import com.actio.actio_api.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    UserRole findByRoleDescription(String roleDescription);
}
