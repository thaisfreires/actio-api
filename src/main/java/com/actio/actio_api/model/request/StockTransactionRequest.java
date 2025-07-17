package com.actio.actio_api.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents the request payload for performing a stock transaction,
 * such as a purchase or sale operation initiated by the client.
 *
 * This DTO is used by REST endpoints to receive transaction parameters
 * and is validated to ensure the required data is present.
 */
@Data
@Builder
public class StockTransactionRequest {

    /**
     * Identifier of the stock involved in the transaction.
     * Must refer to a valid stock registered in the system.
     * Cannot be null.
     */
    @NotNull
    private Long stockId;

    /**
     * Number of stock units to be transacted.
     * Represents the total quantity being bought or sold.
     * Cannot be null.
     */
    @NotNull
    private Integer quantity;

    /**
     * Unit price of the stock at the moment of negotiation.
     * Expressed in euros and required for value calculations.
     * Cannot be null.
     */
    @NotNull
    private BigDecimal value;
}
