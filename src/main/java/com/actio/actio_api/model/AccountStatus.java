package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "account_status")
public class AccountStatus {

    @Id
    @Column(name = "status_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusCode;

    @Column(name = "status_description")
    private String statusDescription;

}
