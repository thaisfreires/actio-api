package com.actio.actio_api.service;

import com.actio.actio_api.model.StockItem;
import com.actio.actio_api.model.StockItemId;
import com.actio.actio_api.repository.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing stock items,
 *
 */
@Service
@RequiredArgsConstructor
public class StockItemService {

    private StockItemRepository stockItemRepository;

    /**
     * Retrieves the quantity of stock units held by a specific account for a given stock.
     * If no StockItem record exists, returns 0.
     *
     * @param accountId the ID of the user's account
     * @param stockId   the ID of the stock
     * @return the number of stock units held, or 0 if none exists
     */
    public Integer getStockItemQuantity(Long accountId, Long stockId) {
        return stockItemRepository.findById(new StockItemId(accountId, stockId))
                .map(StockItem::getQuantity)
                .orElse(0);
    }

}
