package com.actio.actio_api.service;

import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.response.GetAlphaVantageStockResponse;
import com.actio.actio_api.model.response.StockQuantityResponse;
import com.actio.actio_api.model.response.WalletResponse;
import com.actio.actio_api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service class responsible for managing user wallet(stock holdings) operations.
 *
 */

@Service
@RequiredArgsConstructor
public class WalletService {

    private final AccountRepository accountRepository;
    private final AlphaVantageWebClientService  alphaVantageWebClientService;
    private final StockItemService stockItemService;
    private final ActioUserService  actioUserService;


    /**
     * Retrieves the wallet(stock holdings) for a given user, including current market data.
     * @param user to be found
     * @return a list of all WalletResponse objects representing the user's wallet
     */
    public List<WalletResponse> getWalletForUser(ActioUser user) {
        Account account = accountRepository.findByActioUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found for user with id: " + user.getId()));

        return account.getItems().stream()
                .map(item -> {
                    GetAlphaVantageStockResponse stockData = alphaVantageWebClientService
                            .getStock(item.getStock().getStockName())
                            .block();

                    if(stockData == null) {
                        throw new RuntimeException("Failed to retrieve stock data for: " + item.getStock().getStockName());
                    }
                    return new WalletResponse(
                            stockData.getStockId(),
                            stockData.getSymbol(),
                            item.getQuantity(),
                            stockData.getPrice().doubleValue(),
                            stockData.getChangePercent()
                    );
                })
                .collect(Collectors.toList());
    }


    /**
     * Retrieves the quantity of a specific stock held in the authenticated user's active account.
     *
     * @param stockId the ID of the stock to check
     * @return a StockQuantityResponse containing the stock ID and the quantity held by the user
     */
    public StockQuantityResponse getStockQuantityById(Long stockId){
        Long activeAccountId = actioUserService.getAuthenticatedUser().getAccount().getId();
       Integer stockItemQuantity = stockItemService.getStockItemQuantity(activeAccountId, stockId);
       return StockQuantityResponse.builder()
               .stockId(stockId)
               .quantity(stockItemQuantity)
               .build();
    }
}
