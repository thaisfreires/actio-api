package com.actio.actio_api.model.request;

import com.actio.actio_api.validation.annotation.Adult;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * Data transfer object representing a user registration request.
 *
 * This class encapsulates all required fields for creating a new user profile,
 * including validation constraints for structure, format, and business rules.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    /**
     * Full name of the user.
     * Must be between 2 and 250 characters.
     */
    @NotNull
    @Length(min = 2, max = 250)
    private String name;

    /**
     * Portuguese fiscal identification number (NIF).
     * Must be exactly 9 numeric digits and unique.
     */
    @Size(min = 9, max = 9, message = "NIF must contain 9 digits")
    @Pattern(regexp = "\\d{9}", message = "Invalid NIF")
    @NotNull(message = "A valid NIF is required")
    private String nif;

    /**
     * The user's date of birth.
     * Must not be null and the user must be at least 18 years old.
     */
    @NotNull(message = "Date of birth is required")
    @Adult()
    private LocalDate date_of_birth;

    /**
     * The user's email address.
     * Must follow a valid email format.
     */
    @Column(unique = true)
    @NotNull(message = "Email address is required")
    @Email(message = "Invalid email address")
    private String email;

    /**
     * Password for the user's account.
     * Must contain at least 9 characters.
     */
    @NotNull(message = "Password is required")
    @Length(min = 9, message = "Password must be at least 9 characters long")
    private String password;

}
