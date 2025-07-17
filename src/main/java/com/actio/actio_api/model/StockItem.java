package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a specific holding of a stock within a user's account.
 *
 * This entity establishes the ownership and quantity of a particular stock
 * held by an account. It uses a composite primary key combining account ID and stock ID.
 *
 * A database trigger automatically updates the quantity value in response to
 * financial transactions involving the stock (e.g. buy or sell operations),
 * ensuring data consistency without manual updates from the application layer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"account", "stock"})
@Entity
@Table(name = "stock_item")
public class StockItem {

    /**
     * Composite identifier consisting of account ID and stock ID.
     * Uniquely defines the link between an account and a stock.
     */
    @EmbeddedId
    private StockItemId id;

    /**
     * Amount of stock units held by the account.
     * Automatically adjusted by a database trigger upon related transactions.
     */
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * Reference to the owning account.
     * Part of the composite key via mapping.
     */
    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "id_account")
    private Account account;

    /**
     * Reference to the traded stock.
     * Part of the composite key via mapping.
     */
    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "id_stock")
    private Stock stock;
}

