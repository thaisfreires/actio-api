package com.actio.actio_api.service;

import com.actio.actio_api.model.Stock;
import com.actio.actio_api.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.NonUniqueObjectException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class responsible for managing stock-related operations.
 *
 */
@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;


    /**
     * Finds a stock entry by its stock name (symbol).
     * If more than one stock is found with the same name, a NonUniqueObjectException is thrown.
     *
     * @param symbol the stock name used to locate the stock
     * @return the unique Stock object associated with the given symbol
     * @throws NonUniqueObjectException if multiple stocks are found with the given symbol
     */
    public Stock findByStockName(String symbol){
        List<Stock> stocks = stockRepository.findStockByStockName(symbol);
        if(stocks.stream().count() > 1){
            String errorMessage = "No unique stock " + symbol + ", found: ";
            System.out.println("[StockService]: " + errorMessage + stocks.size());
            throw new NonUniqueObjectException(errorMessage, String.valueOf(stocks.size()));
        }
        return stocks.get(0);
    }

    public Stock findByStockId(Long id){
        return stockRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Stock not found, id: " + id));
    }

    /**
     * Saves the provided stock to the database.
     *
     * @param stock the stock object to be saved
     * @return the persisted stock instance
     */
    public Stock save(Stock stock){
        return stockRepository.save(stock);
    }

    /**
     * Checks whether a stock with the specified name already exists in the database.
     *
     * @param symbol the stock name to verify
     * @return true if the stock exists, false otherwise
     */
    public boolean stockIsSaved(String symbol){
        return stockRepository.existsStockByStockName(symbol);
    }

}
