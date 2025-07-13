package com.actio.actio_api.service;

import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.UserRole;
import com.actio.actio_api.model.request.UserRegistrationRequest;
import com.actio.actio_api.model.response.UserRegistrationResponse;
import com.actio.actio_api.repository.ActioUserRepository;
import com.actio.actio_api.repository.UserRoleRepository;
import com.actio.actio_api.validation.FieldValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service responsible for managing operations related to user profile creation.
 *
 * Handles user registration logic including:
 *     Checking for uniqueness of email and NIF
 *     Validating business rules
 *     Encoding passwords
 *     Persisting new users to the database
 * Converts data between request/response DTOs and domain models.
 */
@Service
@AllArgsConstructor
public class ActioUserService {

    private final ActioUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    /**
     * Registers a new user after validating business constraints such as uniqueness of email and NIF.
     *
     * @param request the registration input with personal and authentication details
     * @return a lightweight response containing the user's email and generated ID
     * @throws FieldValidationException if email or NIF already exist
     */
    public UserRegistrationResponse save(UserRegistrationRequest request){
        boolean emailExists = repository.existsActioUserByEmail(request.getEmail());
        boolean nifExists = repository.existsActioUserByNif(request.getNif());
        if (emailExists || nifExists) {
            Map<String, String> errors = new HashMap<>();
            if (emailExists) {
                errors.put("email", "This email is already in use");
            }
            if (nifExists) {
                errors.put("nif", "This NIF is already in use");
            }
            throw new FieldValidationException(errors);
        }

        ActioUser newClient = requestToActioUser(request);
            return actioUserToResponse(repository.save(newClient));
    }

    /**
     * Converts a validated registration request into a {@link ActioUser} entity,
     * encoding the password and setting the specified role.
     *
     * @param request the user registration data
     * @return the corresponding {@link ActioUser} entity ready for persistence
     */
    private ActioUser requestToActioUser(UserRegistrationRequest request) {

        // CLIENT
        UserRole userRole = userRoleRepository.findById(1).orElseThrow();

        return ActioUser.builder()
                .name(request.getName())
                .nif(request.getNif())
                .date_of_birth(request.getDate_of_birth())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(userRole)
                .build();
    }

    /**
     * Converts a persisted {@link ActioUser} into a {@link UserRegistrationResponse}
     * for returning to the client after successful creation.
     *
     * @param actioUser the saved user entity
     * @return a response containing the user's ID and email
     */
    private UserRegistrationResponse actioUserToResponse(ActioUser actioUser) {
        return UserRegistrationResponse.builder()
                .email(actioUser.getEmail())
                .id(actioUser.getId())
                .build();
    }

}
