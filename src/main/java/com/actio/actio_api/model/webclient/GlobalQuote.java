package com.actio.actio_api.model.webclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Represents a real-time stock quote retrieved from the Alpha Vantage API.
 *
 * Each field corresponds to a specific property in the JSON response from Alpha Vantage's GLOBAL_QUOTE function.
 * Values such as price, change, open, high, and low are represented as BigDecimal to ensure financial precision.
 * Volume is modeled as BigInteger due to its potential size.
 *
 */
@Data
@Builder
public class GlobalQuote {

    /**
     * Ticker symbol of the stock (e.g. "BCP.LS", "NOS.LS").
     */
    @JsonProperty("01. symbol")
    private String symbol;

    /**
     * Opening price of the trading day, converted to EUR in service layer.
     */
    @JsonProperty("02. open")
    private BigDecimal open;

    /**
     * Highest price recorded during the trading day.
     */
    @JsonProperty("03. high")
    private BigDecimal high;

    /**
     * Lowest price recorded during the trading day.
     */
    @JsonProperty("04. low")
    private BigDecimal low;

    /**
     * Last traded price of the stock.
     */
    @JsonProperty("05. price")
    private BigDecimal price;

    /**
     * Total number of shares traded during the day.
     */
    @JsonProperty("06. volume")
    private BigInteger volume;

    /**
     * Date of the latest trading activity in YYYY-MM-DD format.
     */
    @JsonProperty("07. latest trading day")
    private String latestTradingDay;

    /**
     * Closing price of the stock from the previous trading day.
     */
    @JsonProperty("08. previous close")
    private BigDecimal previousClose;

    /**
     * Absolute change in price compared to the previous close.
     */
    @JsonProperty("09. change")
    private BigDecimal change;

    /**
     * Percentage change in price compared to the previous close, including possible negative values.
     * Example format: "-1.5720%"
     */
    @JsonProperty("10. change percent")
    private String changePercent;
}