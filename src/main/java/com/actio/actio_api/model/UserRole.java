package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "UserRole")
public class UserRole {

    @Id
    @Column(name = "RoleCode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleCode;

    @Column(name = "RoleDescription")
    private String roleDescription;

    @OneToMany(mappedBy = "userRole")
    private List<ActioUser> users;

}
