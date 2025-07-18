package com.actio.actio_api.controller;

import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.response.StockQuantityResponse;
import com.actio.actio_api.model.response.WalletResponse;
import com.actio.actio_api.service.ActioUserService;
import com.actio.actio_api.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller responsible for handling wallet-related operations for authenticated users.
 * including buying, selling, and retrieving recent transaction history.
 *
 * This controller exposes endpoints to retrieve the current stock holdings ("wallet")
 *  * of a logged-in client. Each item includes the stock name, quantity, current price,
 *  * and daily variation, retrieved from the Alpha Vantage API.
 */

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor

public class WalletController {

    private final WalletService walletService;
    private final ActioUserService  actioUserService;

    /**
     * Retrieves user wallet (list of stock holdings).
     * Accessible to users with CLIENT role.
     *
     */
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    public ResponseEntity<List<WalletResponse>> getWallet(){
        ActioUser user = actioUserService.getAuthenticatedUser();

        List<WalletResponse> wallet = walletService.getWalletForUser(user);
        return ResponseEntity.ok(wallet);

    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{stockId}/quantity")
    public ResponseEntity<StockQuantityResponse> getStockQuantity(@PathVariable Long stockId) {
        StockQuantityResponse response = walletService.getStockQuantityById(stockId);
        return ResponseEntity.ok(response);
    }
}
