package com.actio.actio_api.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class StockTransactionResponse {
    private Long transactionId;
    private String transactionType;
    private Integer transactionTypeId;
    private String stockSymbol;
    private Long stockId;
    private Integer quantity;
    private BigDecimal value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime transactionDateTime;
}
