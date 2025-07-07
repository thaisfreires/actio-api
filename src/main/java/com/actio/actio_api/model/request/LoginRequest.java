package com.actio.actio_api.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object used for user login requests.
 *
 * This class carries the email and password provided by a user attempting
 * to authenticate. Validation annotations ensure both fields are present and correctly formatted.
 */
@Data
@Builder
public class LoginRequest {

    /**
     * The user's email address used for login.
     * Must be a valid email format and cannot be null.
     */
    @Column(unique = true)
    @NotNull(message = "Email address is required to authenticate in the system")
    @Email(message = "Invalid email address")
    private String email;

    /**
     * The user's password.
     * Must not be null.
     */
    @NotNull(message = "Password is required to authenticate in the system")
    private String password;

}
