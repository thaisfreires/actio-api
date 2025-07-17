package com.actio.actio_api.controller;

import com.actio.actio_api.model.response.GetAlphaVantageStockResponse;
import com.actio.actio_api.service.AlphaVantageWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(origins = "*")
public class AlphaVantageWebClientController {

    private final AlphaVantageWebClientService service;

    /**
     * Retrieves a stock quote in EUR based on the provided symbol by querying the Alpha Vantage API.
     *
     * The method performs a synchronous call to the stock retrieval service, which may include
     * currency conversion via Alpha Vantage. If the request is successful, it returns a stock quote
     * response wrapped in an HTTP 200 OK.
     *
     * If the service detects an invalid or unresolvable symbol and throws an IllegalStateException,
     * the response will be a 400 Bad Request with an explanatory error message.
     *
     * Any other unexpected exceptions result in a 500 Internal Server Error response.
     *
     * @param symbol the stock symbol to query (e.g. "NOS.LS", "BCP.LS")
     * @return ResponseEntity containing the stock quote response in EUR on success,
     *         or an error description with appropriate HTTP status code on failure
     */
    @GetMapping("/{symbol}")
    public ResponseEntity<?> getAlphaVantageStock(@PathVariable String symbol) {
        try {
            GetAlphaVantageStockResponse quote = service.getStock(symbol).block();
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
