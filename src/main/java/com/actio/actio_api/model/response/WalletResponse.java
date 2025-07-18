package com.actio.actio_api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data transfer object (DTO) representing a single item in a user's wallet (stock holdings).
 *
 * This DTO is typically returned in the response of the wallet API endpoint and used by the frontend
 * to render the user's portfolio.
 */

@Data
@AllArgsConstructor
public class WalletResponse {
    private Long stockId;
    private String stockName;
    private Integer quantity;
    private double currentValue;
    private String dailyVariation;
}

