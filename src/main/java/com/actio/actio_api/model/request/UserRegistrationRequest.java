package com.actio.actio_api.model.request;

import com.actio.actio_api.validation.annotation.Adult;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * Data transfer object representing a user registration request.
 *
 * This class encapsulates all required fields for creating a new user profile,
 * including validation constraints for structure, format, and business rules.
 */
@Data
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
    @Size(min = 9, max = 9, message = "O NIF deve conter 9 digitos")
    @Pattern(regexp = "\\d{9}", message = "NIF inválido")
    @NotNull(message = "É obrigatório informar um NIF válido")
    private String nif;

    /**
     * The user's date of birth.
     * Must not be null and the user must be at least 18 years old.
     */
    @NotNull(message = "É obrigatório informar a data de nascimento")
    @Adult()
    private LocalDate date_of_birth;

    /**
     * The user's email address.
     * Must follow a valid email format.
     */
    @Column(unique = true)
    @NotNull(message = "É obrigatório informar o endereço de e-mail")
    @Email(message = "Endereço de e-mail inválido")
    private String email;

    /**
     * Password for the user's account.
     * Must contain at least 9 characters.
     */
    @NotNull(message = "É obrigatório definir uma senha")
    @Length(min = 9, message = "A senha deve conter ao menos 9 caracteres")
    private String password;

}
