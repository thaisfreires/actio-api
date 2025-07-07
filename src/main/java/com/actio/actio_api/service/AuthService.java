package com.actio.actio_api.service;

import com.actio.actio_api.model.UserProfile;
import com.actio.actio_api.model.request.LoginRequest;
import com.actio.actio_api.model.response.LoginResponse;
import com.actio.actio_api.repository.UserProfileRepository;
import com.actio.actio_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Service responsible for handling user authentication logic.
 *
 * This service coordinates Spring Security's {@link AuthenticationManager}
 * with the application's persistence layer and JWT token generation logic.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserProfileRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * Authenticates a user using email and password credentials.
     *
     * Upon successful authentication, the user's profile is retrieved from the database
     * and a JWT token is generated. The result is packaged into a {@link LoginResponse}
     * containing both the email and the generated token.
     *
     * @param request the login credentials including email and password
     * @return the authenticated user's email and JWT token
     * @throws AuthenticationException if authentication fails
     * @throws NoSuchElementException if the user is not found after authentication
     */
    public LoginResponse login(LoginRequest request){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String email = authentication.getName();
        UserProfile user = this.findByEmail(request.getEmail());
        String token = jwtUtil.createToken(user);
        return LoginResponse
                .builder()
                .email(email)
                .token(token)
                .build();
    }

    /**
     * Retrieves a user profile by email.
     *
     * @param email the email to search for
     * @return the matching {@link UserProfile}
     * @throws java.util.NoSuchElementException if no user is found
     */
    private  UserProfile findByEmail(String email){
        return repository.findByEmail(email).orElseThrow();
    }
}
