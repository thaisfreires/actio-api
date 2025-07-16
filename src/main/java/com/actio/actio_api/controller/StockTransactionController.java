package com.actio.actio_api.controller;

import com.actio.actio_api.model.request.StockTransactionRequest;
import com.actio.actio_api.model.response.StockTransactionResponse;
import com.actio.actio_api.service.StockTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for handling stock transaction operations,
 * including buying, selling, and retrieving recent transaction history.
 *
 * Endpoints are protected based on user roles. Clients can initiate transactions,
 * while both Clients and Admins can retrieve the transaction list.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class StockTransactionController {

    private final StockTransactionService service;

    /**
     * Endpoint to process a stock purchase transaction.
     * Accessible only to users with CLIENT role.
     *
     * @param request the transaction details provided in the request body
     * @return the transaction response if successful; error message otherwise
     */
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/buy")
    public ResponseEntity<?> buy(@Valid @RequestBody StockTransactionRequest request) {
        try {
            StockTransactionResponse buyTransaction = service.buy(request);
            return ResponseEntity.ok().body(buyTransaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint to process a stock sale transaction.
     * Accessible only to users with CLIENT role.
     *
     * @param request the transaction details provided in the request body
     * @return the transaction response if successful; error message otherwise
     */
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/sell")
    public ResponseEntity<?> sell(@Valid @RequestBody StockTransactionRequest request) {
        try {
            StockTransactionResponse sellTransaction = service.sell(request);
            return ResponseEntity.ok().body(sellTransaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Retrieves a list of up to 200 most recent stock transactions.
     * Accessible to users with CLIENT or ADMIN roles.
     *
     * CLIENT users receive only their own transactions.
     * ADMIN users receive all transactions in the system.
     *
     * @return a list of transaction responses or an error message
     */
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            List<StockTransactionResponse> stocks = service.getAll();
            return ResponseEntity.ok().body(stocks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
