package com.actio.actio_api.repository;

import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Integer> {
    List<Movement> findByAccount(Account account);

}
