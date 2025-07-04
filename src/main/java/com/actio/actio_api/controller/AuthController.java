package com.actio.actio_api.controller;

import com.actio.actio_api.model.request.LoginRequest;
import com.actio.actio_api.model.response.LoginResponse;
import com.actio.actio_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for handling user authentication operations.
 *
 * This controller exposes endpoints related to the login process,
 * validating credentials and returning a JWT token in the Authorization header upon success.
 *
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticates a user based on the provided credentials.
     *
     * Accepts a {@link LoginRequest} object containing the user's email and password.
     * If authentication is successful, a JWT token is returned in the Authorization header
     * with Bearer schema.
     *
     * @param request Login data including email and password
     * @return {@code 200 OK} with Authorization header containing JWT token <br>
     *         {@code 400 Bad Request} if credentials are invalid or authentication fails
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request)  {
        try{

            LoginResponse response = authService.login(request);
            var header = new HttpHeaders();
            header.setBearerAuth(response.getToken());
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(header)
                    .build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
