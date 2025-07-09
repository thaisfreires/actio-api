package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock")
    private Long idStock;

    @Column(name = "stock_name")
    private String stockName;

    @OneToMany(mappedBy = "stock")
    private List<StockItem> items;

}
