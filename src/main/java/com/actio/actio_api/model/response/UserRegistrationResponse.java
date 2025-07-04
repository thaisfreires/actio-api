package com.actio.actio_api.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object representing the response returned
 * after a successful user registration.
 *
 * This class includes minimal identifying information typically
 * used for client-side confirmation or navigation after account creation.
 */
@Data
@Builder
public class UserRegistrationResponse {

    /**
     * The unique identifier of the newly created user.
     */
    private Long id;

    /**
     * The email address associated with the registered user.
     */
    private String email;


}
