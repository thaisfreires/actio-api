package com.actio.actio_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class StockItemId implements Serializable {

    @Column(name = "id_account")
    private Long accountId;

    @Column(name = "id_stock")
    private Long stockId;
}
