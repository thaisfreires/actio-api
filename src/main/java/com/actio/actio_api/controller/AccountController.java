package com.actio.actio_api.controller;

import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.request.AccountStatusUpdateRequest;
import com.actio.actio_api.model.response.AccountResponse;
import com.actio.actio_api.service.AccountService;
import com.actio.actio_api.service.ActioUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ActioUserService actioUserService;

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> deleteAccount() {
        try {
            ActioUser user = actioUserService.getAuthenticatedUser();
            AccountResponse response = accountService.deleteAccount(user);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to delete account" + e.getMessage());
        }
    }


    @PutMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(@RequestBody AccountStatusUpdateRequest request) {
        try{
            AccountResponse response = accountService.updateAccountStatus(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update account");
        }
    }

}
