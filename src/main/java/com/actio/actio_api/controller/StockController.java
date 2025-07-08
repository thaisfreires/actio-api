package com.actio.actio_api.controller;

import com.actio.actio_api.model.webclient.GlobalQuote;
import com.actio.actio_api.service.AlphaVantageWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller responsible for exposing stock quote information via HTTP endpoints.
 *
 * This controller handles requests related to stocks, retrieves real-time quote data
 * from the Alpha Vantage API (via {@link AlphaVantageWebClientService}), and returns
 * the result formatted as JSON.
 *
 * The quotes returned are automatically converted from USD to EUR.
 */
@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final AlphaVantageWebClientService service;

    /**
     * Endpoint that retrieves a stock quote based on the given symbol.
     *
     * @param symbol the stock symbol to query (e.g. "NOS.LS", "BCP.LS")
     * @return ResponseEntity containing the stock quote in EUR if successful,
     *         or an error message with appropriate HTTP status code if any exception occurs
     */
    @GetMapping("/{symbol}")
    public ResponseEntity<?> getStock(@PathVariable String symbol) {
        try {
            GlobalQuote quote = service.getStock(symbol).block();
            return ResponseEntity.ok(quote);
        } catch (IllegalStateException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Could not retrieve stock data", "details", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error occurred", "details", ex.getMessage()));
        }
    }

}
