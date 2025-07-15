package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a publicly traded stock listed on financial markets.
 *
 * This entity models equity assets available for trading or investment within the system.
 * Each stock corresponds to a security listed on a stock exchange, such as NYSE, NASDAQ,
 * Euronext, or B3. The name field typically reflects the company's name or ticker symbol.
 *
 * The relationship with StockItem allows the system to track user-specific holdings or
 * allocation volumes of each stock. This provides a flexible foundation for portfolio
 * management, trading activity, and market analysis.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
public class Stock {

    /**
     * Unique identifier of the stock.
     * Auto-generated primary key by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock")
    private Long idStock;

    /**
     * Designation of the stock.
     * May represent the company name, brand, or market ticker symbol.
     */
    @Column(name = "stock_name", unique = true)
    private String stockName;

    /**
     * List of stock item holdings referencing this stock.
     * Allows tracking ownership or investment allocations across accounts.
     */
    @OneToMany(mappedBy = "stock")
    private List<StockItem> items;

}
