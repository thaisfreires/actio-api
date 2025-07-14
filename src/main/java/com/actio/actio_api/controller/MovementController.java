package com.actio.actio_api.controller;

import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.Movement;
import com.actio.actio_api.model.request.MovementRequest;
import com.actio.actio_api.model.response.MovementResponse;
import com.actio.actio_api.service.ActioUserService;
import com.actio.actio_api.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
public class MovementController {
    private final MovementService  movementService;
    private final ActioUserService actioUserService;

    /**
     * Allows a user with CLIENT role to deposit funds into their active account.
     * @param movementRequest deposit details including amount
     * @return status HTTP 200 if successfull and an exception if fails.
     */
    @PostMapping("/deposit")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> deposit (@RequestBody MovementRequest movementRequest){

        try{
            ActioUser user = actioUserService.getAuthenticatedUser();
            MovementResponse response = movementService.deposit(user, movementRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Failed Deposit");
        }

    }
    /**
     * Allows a user with CLIENT role to withdraw funds from their active account.
     *
     * @param movementRequest withdrawal details including amount
     * @return status HTTP 200 if successfull and an exception if fails.
     */
    @PostMapping("/rescue")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> rescue(@RequestBody MovementRequest movementRequest) {
        try{
            ActioUser user = actioUserService.getAuthenticatedUser();
            MovementResponse response =movementService.withdrawal(user, movementRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Failed Withdrawal");
        }
    }
    /**
     * Returns movement history based on the user's role:
     * - CLIENT: only their personal history
     * - ADMIN: the most recent 200 movements
     */
    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<List<Movement>> getMovements() {
        ActioUser user = actioUserService.getAuthenticatedUser();
        List<Movement> history = movementService.getHistory(user);
        return ResponseEntity.ok(history);
    }
}
