package com.actio.actio_api.service;

import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.AccountStatus;
import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.request.AccountStatusUpdateRequest;
import com.actio.actio_api.model.response.AccountResponse;
import com.actio.actio_api.repository.AccountRepository;
import com.actio.actio_api.repository.AccountStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountStatusRepository accountStatusRepository;
    private final StockItemService stockItemService;

    /**
     * Creates and saves a new account for the given user with ACTIVE status and zero balance.
     *
     * @param user The user for whom the account is being created.
     * @return AccountResponse containing the new account's details.
     */
    public AccountResponse save(ActioUser user) {
        Account account = new Account();
        account.setActioUser(user);
        account.setStatus(accountStatusRepository.findByStatusDescription("ACTIVE").orElseThrow(()-> new NoSuchElementException("Status not found")));
        account.setCurrentBalance(BigDecimal.valueOf(0));

        Account saved = accountRepository.save(account);

        return AccountResponse.builder()
                .id(saved.getId())
                .userEmail(user.getEmail())
                .status(saved.getStatus().getStatusDescription())
                .currentBalance(saved.getCurrentBalance())
                .build();
    }

    /**
     * Deletes the user's active account if it has no stock items and zero balance.
     * The account status is changed to CLOSED.
     *
     * @param user The user requesting account deletion.
     * @return AccountResponse with updated account information.
     * @throws RuntimeException if the account has stock items, non-zero balance, or is not ACTIVE.
     */
    public AccountResponse deleteAccount(ActioUser user) {
        Account account = getActiveAccount(user);

        boolean hasItems = stockItemService.hasActiveStockItemsForAccount(account);
        if (hasItems) throw new RuntimeException("Account has active stock items");

        if (account.getCurrentBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new RuntimeException("Account balance must be zero");
        }

        account.setStatus(accountStatusRepository
                .findByStatusDescription("CLOSED")
                .orElseThrow(()-> new NoSuchElementException("Status not found")));

        Account updated = accountRepository.save(account);

        return AccountResponse.builder()
                .id(updated.getId())
                .userEmail(user.getEmail())
                .status(updated.getStatus().getStatusDescription())
                .currentBalance(updated.getCurrentBalance())
                .build();
    }

    /**
     * Updates the status of an account based on the given request.
     *
     * @param request The request containing account ID and the new status to apply.
     * @return AccountResponse with updated account status.
     * @throws RuntimeException if account doesn't exist, the status is invalid,
     *                          or the account is already CLOSED.
     */
    public AccountResponse updateAccountStatus(AccountStatusUpdateRequest request) {

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if ("CLOSED".equals(account.getStatus().getStatusDescription())) {
            throw new RuntimeException("Cannot update a closed account");
        }

        AccountStatus newStatus = new AccountStatus();
        newStatus.setStatusDescription(request.getNewStatus());
        account.setStatus(newStatus);

        Account updated = accountRepository.save(account);

        return AccountResponse.builder()
                .id(updated.getId())
                .userEmail(updated.getActioUser().getEmail())
                .status(updated.getStatus().getStatusDescription())
                .currentBalance(updated.getCurrentBalance())
                .build();
    }

    /**
     * Retrieves the user's active account.
     *
     * @param user The user whose active account is requested.
     * @return The active Account object.
     * @throws RuntimeException if the account does not exist or is not ACTIVE.
     */
    private Account getActiveAccount(ActioUser user) {
        Account account = accountRepository.findByActioUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!"ACTIVE".equals(account.getStatus().getStatusDescription())) {
            throw new RuntimeException("Account is not ACTIVE");
        }

        return account;
    }

    /**
     * Retrieves detailed account information for the authenticated user.
     *
     * @return an AccountResponse containing the account ID, user email, and current balance
     */
    public AccountResponse getAccountInfo(ActioUser user){
        Account account = this.getActiveAccount(user);
        return AccountResponse.builder()
                .id(account.getId())
                .userEmail(account.getActioUser().getEmail())
                .status(account.getStatus().getStatusDescription())
                .currentBalance(account.getCurrentBalance())
                .build();
    }

    public Account getById(Long id){
        return  accountRepository.findAccountById((id));

    }
}
