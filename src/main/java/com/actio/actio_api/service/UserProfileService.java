package com.actio.actio_api.service;

import com.actio.actio_api.enums.Role;
import com.actio.actio_api.model.UserProfile;
import com.actio.actio_api.model.request.UserRegistrationRequest;
import com.actio.actio_api.model.response.UserRegistrationResponse;
import com.actio.actio_api.repository.UserProfileRepository;
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
public class UserProfileService {

    private final UserProfileRepository repository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user after validating business constraints such as uniqueness of email and NIF.
     *
     * @param request the registration input with personal and authentication details
     * @return a lightweight response containing the user's email and generated ID
     * @throws FieldValidationException if email or NIF already exist
     */
    public UserRegistrationResponse save(UserRegistrationRequest request){
        boolean emailExists = repository.existsUserProfileByEmail(request.getEmail());
        boolean nifExists = repository.existsUserProfileByNif(request.getNif());
        if (emailExists || nifExists) {
            Map<String, String> errors = new HashMap<>();
            if (emailExists) {
                errors.put("email", "Este e-mail já está em uso");
            }
            if (nifExists) {
                errors.put("nif", "Este NIF já está em uso");
            }
            throw new FieldValidationException(errors);
        }

        UserProfile newClient = requestToUserProfile(request, Role.CLIENT);
            return userProfileToResponse(repository.save(newClient));
    }

    /**
     * Converts a validated registration request into a {@link UserProfile} entity,
     * encoding the password and setting the specified role.
     *
     * @param request the user registration data
     * @param role the role to assign to the new user
     * @return the corresponding {@link UserProfile} entity ready for persistence
     */
    private UserProfile requestToUserProfile(UserRegistrationRequest request, Role role) {
        return UserProfile.builder()
                .name(request.getName())
                .nif(request.getNif())
                .date_of_birth(request.getDate_of_birth())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
    }

    /**
     * Converts a persisted {@link UserProfile} into a {@link UserRegistrationResponse}
     * for returning to the client after successful creation.
     *
     * @param userProfile the saved user entity
     * @return a response containing the user's ID and email
     */
    private UserRegistrationResponse userProfileToResponse(UserProfile userProfile) {
        return UserRegistrationResponse.builder()
                .email(userProfile.getEmail())
                .id(userProfile.getId())
                .build();
    }

}
