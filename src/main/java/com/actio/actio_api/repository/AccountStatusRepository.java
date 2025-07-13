package com.actio.actio_api.repository;

import com.actio.actio_api.model.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, Integer> {
    Optional<AccountStatus> findByStatusDescription(String description);
}
