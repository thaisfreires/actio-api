package com.actio.actio_api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementResponse {
    private Long id;
    private BigDecimal amount;
    private String type;
    private LocalDateTime dateTime;
}
