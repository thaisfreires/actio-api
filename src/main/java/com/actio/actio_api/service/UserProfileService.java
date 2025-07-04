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

@Service
@AllArgsConstructor
public class UserProfileService {

    private final UserProfileRepository repository;
    private final PasswordEncoder passwordEncoder;

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

    private UserRegistrationResponse userProfileToResponse(UserProfile userProfile) {
        return UserRegistrationResponse.builder()
                .email(userProfile.getEmail())
                .id(userProfile.getId())
                .build();
    }

}
