package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "movement_type")
@Data
public class MovementType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_code")
    private Integer typeCode;

    @Column(name = "type_description")
    private String typeDescription;

    @OneToMany(mappedBy = "movementType")
    private List<Movement> movements;

}
