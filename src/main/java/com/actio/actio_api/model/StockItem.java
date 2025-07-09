package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stock_item")
public class StockItem {

    @EmbeddedId
    private StockItemId id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "id_account")
    private Account account;

    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "id_stock")
    private Stock stock;
}

