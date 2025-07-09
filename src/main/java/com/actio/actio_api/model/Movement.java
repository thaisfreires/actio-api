package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "movement")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movement")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "type_code")
    private MovementType movementType;

    @Column(name = "movement_date_time")
    private LocalDateTime movementDateTime;
}
