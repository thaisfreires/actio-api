package com.actio.actio_api.model;

import com.actio.actio_api.enums.Role;
import com.actio.actio_api.validation.annotation.Adult;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * Entity representing a user profile stored in the database.
 *
 * This model includes user identity, authentication credentials,
 * personal information, and role-based access. Validation annotations
 * are applied to enforce business rules and data consistency.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    /**
     * The primary key identifier of the user profile.
     * Generated automatically by the persistence provider.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Full name of the user.
     * Must be between 2 and 250 characters.
     */
    @NotNull
    @Length(min = 2, max = 250)
    private String name;

    /**
     * Portuguese tax identification number (NIF).
     * Must be exactly 9 numeric digits and unique.
     */
    @Size(min = 9, max = 9, message = "NIF must contain 9 digits")
    @Pattern(regexp = "\\d{9}", message = "Invalid NIF")
    @NotNull(message = "A valid NIF is required")
    private String nif;

    /**
     * User’s date of birth.
     * Must not be null and user must be 18 years or older.
     */
    @NotNull(message = "Date of birth is required")
    @Adult()
    private LocalDate date_of_birth;

    /**
     * Email address used for authentication and identification.
     * Must be in valid format and unique across users.
     */
    @Column(unique = true)
    @NotNull(message = "Email address is required")
    @Email(message = "Invalid email address")
    private String email;

    /**
     * Encrypted password for account access.
     * Must be at least 9 characters long.
     */
    @NotNull(message = "Password is required")
    @Length(min = 9, message = "Password must be at least 9 characters long")
    private String password;

    /**
     * Role assigned to the user for access control and authorization.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

}
