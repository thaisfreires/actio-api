package com.actio.actio_api.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a role assigned to a user within the system.
 *
 * This entity defines access levels and permissions by categorizing users into roles,
 * such as "ADMIN", "CLIENT", or "CONSULTANT". Roles control visibility, actions, and
 * features available to each user type, facilitating secure and scalable authorization.
 *
 * The ActioUser entity references this class to assign specific capabilities or restrictions.
 */
@Data
@Entity
@Table(name = "user_role")
public class UserRole {

    /**
     * Unique identifier for the role.
     * Auto-generated by the database and used to reference the role in user profiles.
     */
    @Id
    @Column(name = "role_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleCode;

    /**
     * Human-readable description of the role.
     * Typically used for UI display and access control logic.
     */
    @Column(name = "role_description")
    private String roleDescription;


}
