package com.actio.actio_api.repository;

import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.StockItem;
import com.actio.actio_api.model.StockItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, StockItemId> {
    boolean existsByAccount(Account account);

    List<StockItem> findByAccountId(Long id);
}
