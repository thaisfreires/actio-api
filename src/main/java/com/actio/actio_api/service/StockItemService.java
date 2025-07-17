package com.actio.actio_api.service;


import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.StockItem;
import com.actio.actio_api.model.StockItemId;
import com.actio.actio_api.repository.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockItemService {
    private final StockItemRepository stockItemRepository;

    /**
     * Checks if any StockItems exist for the given Account.
     *
     * @param account The account to check for associated StockItems.
     * @return true if there is at least one StockItem for the account; false otherwise.
     */
    public boolean hasStockItemsForAccount(Account account) {
        return stockItemRepository.existsByAccount(account);
    }
    
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
