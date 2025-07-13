package com.actio.actio_api.repository;

import com.actio.actio_api.model.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, Integer> {
}
