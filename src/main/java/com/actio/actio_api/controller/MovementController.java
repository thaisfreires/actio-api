package com.actio.actio_api.controller;

import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.model.Movement;
import com.actio.actio_api.model.request.MovementRequest;
import com.actio.actio_api.repository.ActioUserRepository;
import com.actio.actio_api.security.JwtAuthorizationFilter;
import com.actio.actio_api.security.JwtUtil;
import com.actio.actio_api.service.MovementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
public class MovementController {
    private final MovementService  movementService;
    private final ActioUserRepository actioUserRepository;
    private final JwtUtil jwtUtil;

    /**
     * Allows a user with CLIENT role to deposit funds into their active account.
     * @param movementRequest deposit details including amount
     * @return a success message or an error if validation fails
     */
    @PostMapping("/deposit")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> deposit (@RequestBody MovementRequest movementRequest){

        ActioUser user = getAuthenticatedUser();
        movementService.deposit(user, movementRequest);
        return ResponseEntity.ok("Deposit Successful");
    }
    /**
     * Allows a user with CLIENT role to withdraw funds from their active account.
     *
     * @param movementRequest withdrawal details including amount
     * @return a success message or an error if balance is insufficient or account is inactive
     */
    @PostMapping("/rescue")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> rescue(@RequestBody MovementRequest movementRequest) {

        ActioUser user = getAuthenticatedUser();
        movementService.withdrawal(user, movementRequest);
        return ResponseEntity.ok("Withdrawal successful.");
    }
    /**
     * Returns movement history based on the user's role:
     * - CLIENT: only their personal history
     * - ADMIN: the most recent 200 movements
     */
    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<List<Movement>> getMovements() {
        ActioUser user = getAuthenticatedUser();
        List<Movement> history = movementService.getHistory(user);
        return ResponseEntity.ok(history);
    }

    /**
     * Retrieves the authenticated user from the SecurityContext using their JWT token.
     *
     * @return the authenticated ActioUser
     */
    public ActioUser getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return actioUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
