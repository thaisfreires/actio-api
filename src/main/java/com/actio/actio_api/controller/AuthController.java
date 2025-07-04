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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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
