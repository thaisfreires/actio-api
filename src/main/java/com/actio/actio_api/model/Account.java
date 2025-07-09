package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private ActioUser actioUser;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @ManyToOne
    @JoinColumn(name = "status_code")
    private AccountStatus status;

    @OneToMany(mappedBy = "account")
    private List<StockItem> items;

}
