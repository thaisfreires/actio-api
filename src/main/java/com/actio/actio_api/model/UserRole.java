package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @Column(name = "role_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleCode;

    @Column(name = "role_description")
    private String roleDescription;

    @OneToMany(mappedBy = "userRole")
    private List<ActioUser> users;

}
