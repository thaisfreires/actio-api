package com.actio.actio_api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response object representing account details returned to the client.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    /**
     * The unique identifier of the account.
     */
    private Long id;
    /**
     * The email of the user associated with the account.
     */
    private String userEmail;
    /**
     * The current status of the account (e.g., ACTIVE, BLOCKED, CANCELED).
     */
    private String status;
    /**
     * The current balance available in the account.
     */
    private BigDecimal currentBalance;
}
