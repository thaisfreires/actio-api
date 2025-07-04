package com.actio.actio_api.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object representing the response returned after a successful login.
 *
 * This class contains the authenticated user's email and the generated JWT token
 * used for subsequent authenticated requests.
 */
@Data
@Builder
public class LoginResponse {
    /**
     * The authenticated user's email address.
     */
    private String email;
    /**
     * The JWT token to be used for authorization.
     */
    private String token;
}
