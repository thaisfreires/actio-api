package com.actio.actio_api.model.request;

import lombok.Data;

/**
 * Request object used by administrators to update the status of a user account.
 */
@Data
public class AccountStatusUpdateRequest {
    /**
     * The ID of the account to be updated.
     */
    private Long accountId;
    /**
     * The new status to apply to the account (e.g., ACTIVE, BLOCKED, CANCELED).
     */
    private String newStatus;
}
