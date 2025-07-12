package com.actio.actio_api.repository;

import com.actio.actio_api.model.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {
}
