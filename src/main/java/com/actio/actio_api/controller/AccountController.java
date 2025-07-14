package com.actio.actio_api.controller;

import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.request.AccountRequest;
import com.actio.actio_api.model.response.AccountResponse;
import com.actio.actio_api.model.response.MovementResponse;
import com.actio.actio_api.repository.ActioUserRepository;
import com.actio.actio_api.security.JwtUtil;
import com.actio.actio_api.service.AccountService;
import com.actio.actio_api.service.ActioUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final JwtUtil jwtUtil;
    private final ActioUserService actioUserService;

    @PostMapping("/cancel")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> deleteAccount(@RequestBody AccountRequest request) {
        try {
            ActioUser user = actioUserService.getAuthenticatedUser();
            AccountResponse response = accountService.deleteAccount(user, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete account");
        }
    }
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                                        @RequestBody AccountRequest request) {
        try{
            ActioUser user = actioUserService.getAuthenticatedUser();
            AccountResponse response = accountService.updateAccountStatus(id, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update account");
        }
    }

}
