package com.actio.actio_api.service;

import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.AccountStatus;
import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.request.AccountRequest;
import com.actio.actio_api.model.request.AccountStatusUpdateRequest;
import com.actio.actio_api.model.response.AccountResponse;
import com.actio.actio_api.repository.AccountRepository;
import com.actio.actio_api.repository.AccountStatusRepository;
import com.actio.actio_api.repository.ActioUserRepository;
import com.actio.actio_api.repository.StockItemRepository;
import com.actio.actio_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final StockItemRepository stockItemRepository;
    private final AccountStatusRepository accountStatusRepository;

    public AccountResponse save(ActioUser user) {
        AccountStatus defaultStatus = accountStatusRepository.findByStatusDescription("ACTIVE")
                .orElseThrow(() -> new RuntimeException("AccountStatus ACTIVE not found"));

        Account account = new Account();
        account.setActioUser(user);
        account.setStatus(defaultStatus);
        account.setCurrentBalance(BigDecimal.ZERO);

        Account saved = accountRepository.save(account);

        return AccountResponse.builder()
                .id(saved.getId())
                .userEmail(user.getEmail())
                .status(saved.getStatus().getStatusDescription())
                .currentBalance(saved.getCurrentBalance())
                .build();
    }

    public AccountResponse deleteAccount(ActioUser user) {
        Account account = getActiveAccount(user);

        boolean hasItems = stockItemRepository.existsByAccount(account);
        if (hasItems) throw new RuntimeException("Account has active stock items");

        if (account.getCurrentBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new RuntimeException("Account balance must be zero");
        }

        AccountStatus closed = accountStatusRepository.findByStatusDescription("CLOSED")
                .orElseThrow(() -> new RuntimeException("AccountStatus CANCELLED not found"));
        account.setStatus(closed);

        Account updated = accountRepository.save(account);

        return AccountResponse.builder()
                .id(updated.getId())
                .userEmail(user.getEmail())
                .status(updated.getStatus().getStatusDescription())
                .currentBalance(updated.getCurrentBalance())
                .build();
    }
    public AccountResponse updateAccountStatus(AccountStatusUpdateRequest request) {

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        AccountStatus newStatus = accountStatusRepository.findByStatusDescription(request.getNewStatus())
                .orElseThrow(() -> new RuntimeException("Invalid status ID"));

        if (account.getStatus().getStatusDescription().equals("CLOSED")) {
            throw new RuntimeException("Cannot update a cancelled account");
        }

        account.setStatus(newStatus);
        Account updated = accountRepository.save(account);

        return AccountResponse.builder()
                .id(updated.getId())
                .userEmail(updated.getActioUser().getEmail())
                .status(updated.getStatus().getStatusDescription())
                .currentBalance(updated.getCurrentBalance())
                .build();
    }
    private Account getActiveAccount(ActioUser user) {
        Account account = accountRepository.findByActioUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        AccountStatus active = accountStatusRepository.findByStatusDescription("ACTIVE")
                .orElseThrow(() -> new RuntimeException("AccountStatus ACTIVE not found"));

        if (!account.getStatus().equals(active)) {
            throw new RuntimeException("Account is not ACTIVE");
        }

        return account;
    }

}
