package com.actio.actio_api.repository;

import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.ActioUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByActioUser(ActioUser user);
    Optional<Account> findById(Long accountId);
}
