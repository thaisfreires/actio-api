package com.actio.actio_api.service;

import com.actio.actio_api.model.Account;
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

    private final StockItemRepository stockItemRepository;


    public Integer getStockItemQuantity(Long accountId, Long stockId) {
        return stockItemRepository.findById(new StockItemId(accountId, stockId))
                .map(StockItem::getQuantity)
                .orElse(0);
    }
    public boolean hasActiveStockItemsForAccount(Account account) {
        return stockItemRepository
                .findByAccountId(account.getId())
                .stream()
                .anyMatch(s -> s.getQuantity() > 0);
    }

}
