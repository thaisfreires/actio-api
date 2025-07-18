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

/**
 * Controller responsible for handling account-related operations.
 */
@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ActioUserService actioUserService;

    /**
     * Deletes the authenticated user's account.
     *
     * @return ResponseEntity containing the account deletion response if successful,
     *         or an error message if the operation fails.
     */
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

    /**
     * Updates the status of a user account. Only accessible to users with ADMIN role.
     *
     * @param request the request object containing the account ID and the new status
     * @return ResponseEntity containing the updated account response if successful,
     *         or an error message if the operation fails.
     */
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

    /**
     * Retrieves account information for the authenticated user.
     *
     * Accessible only to users with the CLIENT role.
     *
     * @return a ResponseEntity containing an AccountResponse with account details if successful,
     *         or a bad request response with an error message if retrieval fails
     */
    @GetMapping("/info")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> getAccountInfo(){
        try{
            ActioUser user = actioUserService.getAuthenticatedUser();
            AccountResponse response = accountService.getAccountInfo(user);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to find account info");
        }
    }


}
