package com.actio.actio_api.repository;

import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {
    List<StockTransaction> findByAccount(Account account);
}
