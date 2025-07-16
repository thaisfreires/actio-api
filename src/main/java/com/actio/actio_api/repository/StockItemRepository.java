package com.actio.actio_api.repository;

import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepository extends JpaRepository<StockItem, Integer> {
    boolean existsByAccount(Account account);
}