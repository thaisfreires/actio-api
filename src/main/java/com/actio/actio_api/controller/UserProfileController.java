package com.actio.actio_api.controller;

import com.actio.actio_api.model.request.UserRegistrationRequest;
import com.actio.actio_api.model.response.UserRegistrationResponse;
import com.actio.actio_api.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserProfileController {

    private final UserProfileService service;

    @PostMapping("/save")
    public ResponseEntity<?> saveClient(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            UserRegistrationResponse newClient = service.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
        } catch (IllegalArgumentException | DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
