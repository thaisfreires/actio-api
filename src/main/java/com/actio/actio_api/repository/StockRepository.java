package com.actio.actio_api.repository;

import com.actio.actio_api.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findStockByStockName(String stockName);

    boolean existsStockByStockName(String stockName);
}
