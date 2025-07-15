package com.actio.actio_api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO representing the response structure for stock data retrieved from Alpha Vantage API.
 * Holds basic information about a stock including its identifier, symbol, current price,
 * and percent change.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAlphaVantageStockResponse {

    /**
     * Unique identifier for the stock.
     */
    private Long stockId;

    /**
     * Company name.
     */
    private String company;

    /**
     * Stock symbol (ticker).
     */
    private String symbol;

    /**
     * Current price of the stock.
     */
    private BigDecimal price;

    /**
     * Percentage change since last day in stock price.
     */
    private String changePercent;

}
