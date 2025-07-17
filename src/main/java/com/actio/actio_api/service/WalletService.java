package com.actio.actio_api.service;

import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.response.GetAlphaVantageStockResponse;
import com.actio.actio_api.model.response.WalletResponse;
import com.actio.actio_api.model.webclient.GlobalQuote;
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
                            stockData.getSymbol(),
                            item.getQuantity(),
                            stockData.getPrice().doubleValue(),
                            stockData.getChangePercent()
                    );
                })
                .collect(Collectors.toList());
    }
}
