package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "stock_transaction")
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_stock")
    private Stock stock;

    @Column(name = "negotiation_price")
    private BigDecimal negotiationPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "type_code")
    private TransactionType transactionType;

    @Column(name = "transaction_date_time")
    private LocalDateTime transactionDateTime;
}
