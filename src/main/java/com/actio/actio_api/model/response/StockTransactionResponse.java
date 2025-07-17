package com.actio.actio_api.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StockTransactionResponse {
    private Long transactionId;
    private Long stockId;
    private Integer quantity;
    private BigDecimal value;
    private BigDecimal totalValue;
}
