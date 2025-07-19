package com.actio.actio_api.service;

import com.actio.actio_api.model.*;
import com.actio.actio_api.model.request.StockTransactionRequest;
import com.actio.actio_api.model.response.StockTransactionResponse;
import com.actio.actio_api.repository.StockTransactionRepository;
import com.actio.actio_api.repository.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class responsible for managing stock transactions,
 * including purchase, sale, validation, persistence and conversion to response DTOs.
 *
 * This layer encapsulates the business rules related to transaction processing,
 * ensuring user permissions, account balance, and stock holdings are properly validated
 * before a transaction is executed.
 */
@Service
@RequiredArgsConstructor
public class StockTransactionService {

    private final TransactionTypeRepository transactionTypeRepository;
    private final ActioUserService actioUserService;
    private final StockService stockService;
    private final StockItemService stockItemService;
    private final StockTransactionRepository stockTransactionRepository;

    /**
     * Executes a purchase operation for a stock transaction.
     * Validates the user's balance before proceeding.
     *
     * @param request the input DTO containing transaction parameters
     * @return the registered transaction details
     */
    public StockTransactionResponse buy(StockTransactionRequest request) {
        TransactionType transactionType = transactionTypeRepository
                .findByTypeDescription("BUY")
                .orElseThrow(() -> new RuntimeException("Transaction type BUY not found"));

        return this.newTransaction(request, transactionType);
    }

    /**
     * Executes a sale operation for a stock transaction.
     * Validates stock ownership before proceeding.
     *
     * @param request the input DTO containing transaction parameters
     * @return the registered transaction details
     */
    public StockTransactionResponse sell(StockTransactionRequest request) {
        TransactionType transactionType = transactionTypeRepository
                .findByTypeDescription("SELL")
                .orElseThrow(() -> new RuntimeException("Transaction type SELL not found"));

        return this.newTransaction(request, transactionType);
    }

    /**
     * Retrieves up to 200 most recent stock transactions visible to the authenticated user.
     * Client users receive only their own transactions; Admins see all.
     *
     * @return a list of transaction response DTOs
     */
    public List<StockTransactionResponse> getAll() {
        ActioUser loggedUser = actioUserService.getAuthenticatedUser();
        String role = loggedUser.getUserRole().getRoleDescription();

        List<StockTransaction> transactions;

        switch (role) {
            case "CLIENT" -> transactions = stockTransactionRepository
                    .findByAccount(loggedUser.getAccount());
            case "ADMIN" -> transactions = stockTransactionRepository.findAll();
            default -> throw new NoSuchElementException("Unexpected user role: " + role);
        }

        return transactions.stream()
                .sorted(Comparator.comparing(StockTransaction::getTransactionDateTime).reversed())
                .limit(200)
                .map(this::transactionToResponse)
                .toList();
    }

    /**
     * Internal handler to process and persist a stock transaction.
     * Delegates validation and construction steps.
     *
     * @param request transaction data sent by user
     * @param transactionType enum-like entity describing operation type
     * @return transaction response DTO
     */
    private StockTransactionResponse newTransaction(StockTransactionRequest request, TransactionType transactionType) {
        ActioUser user = actioUserService.getAuthenticatedUser();
        Account account = user.getAccount();
        Stock stock = stockService.findByStockId(request.getStockId());
        Integer quantity = request.getQuantity();
        BigDecimal unitPrice = request.getValue();
        BigDecimal totalValue = calculateTotalValue(quantity, unitPrice);

        validateTransaction(account, stock.getIdStock(), transactionType, quantity, totalValue);

        StockTransaction transaction = buildTransaction(account, stock, transactionType, quantity, unitPrice);
        return transactionToResponse(stockTransactionRepository.save(transaction));
    }

    /**
     * Validates the transaction before execution.
     * - BUY: checks account balance
     * - SELL: checks stock holdings
     *
     * @param account    authenticated user's account
     * @param stockId    target stock ID
     * @param type       transaction type (BUY or SELL)
     * @param quantity   amount of stocks involved
     * @param totalValue total cost or revenue of the operation
     */
    private void validateTransaction(Account account, Long stockId, TransactionType type,
                                     Integer quantity, BigDecimal totalValue) {

        switch (type.getTypeDescription().toUpperCase()) {
            case "BUY" -> {
                if (account.getCurrentBalance().compareTo(totalValue) < 0) {
                    throw new IllegalStateException("Insufficient balance to complete the purchase.");
                }
            }
            case "SELL" -> {
                int holdings = stockItemService.getStockItemQuantity(account.getId(), stockId);
                if (holdings < quantity) {
                    throw new IllegalStateException("You do not have enough shares to sell.");
                }
            }
            default -> throw new IllegalArgumentException("Unsupported transaction type: " + type.getTypeDescription());
        }
    }

    /**
     * Assembles a StockTransaction entity with necessary fields.
     *
     * @param account   account performing the operation
     * @param stock     stock being traded
     * @param type      type of transaction
     * @param quantity  number of units involved
     * @param unitPrice negotiated price per unit
     * @return a prepared entity ready to be saved
     */
    private StockTransaction buildTransaction(Account account, Stock stock, TransactionType type,
                                              Integer quantity, BigDecimal unitPrice) {

        return StockTransaction.builder()
                .account(account)
                .stock(stock)
                .transactionType(type)
                .quantity(quantity)
                .negotiationPrice(unitPrice)
                .transactionDateTime(LocalDateTime.now())
                .build();
    }

    /**
     * Calculates the total value of the transaction.
     *
     * @param quantity  stock quantity involved
     * @param unitPrice price per stock unit
     * @return total transaction value
     */
    private BigDecimal calculateTotalValue(Integer quantity, BigDecimal unitPrice) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Converts an entity into a response DTO for API output.
     *
     * @param transaction the persisted transaction entity
     * @return a structured response object
     */
    private StockTransactionResponse transactionToResponse(StockTransaction transaction) {
        return StockTransactionResponse.builder()
                .transactionId(transaction.getId())
                .stockId(transaction.getStock().getIdStock())
                .quantity(transaction.getQuantity())
                .value(transaction.getNegotiationPrice())
                .totalValue(transaction.getNegotiationPrice().multiply(BigDecimal.valueOf(transaction.getQuantity())))
                .build();
    }

    public void saveTransaction(StockTransaction transaction) {
        stockTransactionRepository.save(transaction);
    }
}
