package com.actio.actio_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Composite key class for the StockItem entity.
 *
 * This embeddable class defines the unique identifier for a stock holding,
 * combining the account and stock IDs to distinguish each user’s ownership of a particular stock.
 *
 * It enables precise mapping and indexing of stock-item relationships in the database.
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class StockItemId implements Serializable {

    /**
     * Identifier of the account that holds the stock.
     */
    @Column(name = "id_account")
    private Long accountId;

    /**
     * Identifier of the stock being held.
     */
    @Column(name = "id_stock")
    private Long stockId;
}
