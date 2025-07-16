package com.actio.actio_api.controller;

import com.actio.actio_api.model.Account;
import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.request.UserRegistrationRequest;
import com.actio.actio_api.model.response.UserInfoResponse;
import com.actio.actio_api.model.response.UserRegistrationResponse;
import com.actio.actio_api.service.ActioUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for managing operations related to user profiles.
 *
 * This controller will handle endpoints for user registration, retrieval, updates,
 * and other user-related actions. Input is validated using standard and custom annotations,
 * and appropriate responses are returned based on business rules and persistence results.
 */
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ActioUserController {

    private final ActioUserService service;

    /**
     * Registers a new user profile.
     *
     * Expects a valid {@link UserRegistrationRequest} payload. If all constraints are met,
     * a new user is created and a {@link UserRegistrationResponse} is returned with HTTP 201.
     * If validations fail or persistence issues occur (e.g. constraint violations),
     * a 400 Bad Request is returned with relevant error details.
     *
     * @param request the new user registration data
     * @return {@code 201 Created} if user is successfully saved<br>
     *         {@code 400 Bad Request} with validation or integrity error message
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveClient(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            UserRegistrationResponse newClient = service.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
        } catch (IllegalArgumentException | DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/user-info")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserInfo() {
        try{
            ActioUser user = service.getAuthenticatedUser();

            Account account = user.getAccount();

            UserInfoResponse response = UserInfoResponse.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .userRole(user.getUserRole().getRoleDescription())
                    .accountId(account.getId())
                    .build();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch user info");
        }

    }

}
