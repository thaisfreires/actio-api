package com.actio.actio_api.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockQuantityResponse {
    private Long stockId;
    private Integer quantity;

}
