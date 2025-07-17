package com.actio.actio_api.service;

import com.actio.actio_api.model.Account;
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
    public boolean hasActiveStockItemsForAccount(Account account) {
        return stockItemRepository
                .findByAccountId(account.getId())
                .stream()
                .anyMatch(s -> s.getQuantity() > 0);
    }
}
