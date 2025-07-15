package com.actio.actio_api.repository;

import com.actio.actio_api.model.StockItem;
import com.actio.actio_api.model.StockItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepository extends JpaRepository<StockItem, StockItemId> {

}
