package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction_type")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_code")
    private Integer typeCode;

    @Column(name = "type_description")
    private String typeDescription;

}
