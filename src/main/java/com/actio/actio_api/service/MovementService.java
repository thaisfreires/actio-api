package com.actio.actio_api.service;

import com.actio.actio_api.model.*;
import com.actio.actio_api.model.request.MovementRequest;
import com.actio.actio_api.model.response.MovementResponse;
import com.actio.actio_api.repository.*;
import com.actio.actio_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    private final MovementTypeRepository movementTypeRepository;
    private final AccountStatusRepository accountStatusRepository;

    /**
     * Creates a deposit operation based on active user.
     * @param user retrieved
     * @param request credentials needed for the transation
     * @return movement
     */
    public MovementResponse deposit(ActioUser user, MovementRequest request) {

        Account account = getActiveAccount(user);

        MovementType depositType = movementTypeRepository.findByTypeDescription("DEPOSIT")
                .orElseThrow(() -> new RuntimeException("MovementType DEPOSIT not found"));

        Movement movement = Movement.builder()
                .account(account)
                .amount(request.getAmount())
                .movementType(depositType)
                .movementDateTime(LocalDateTime.now())
                .build();
        Movement saved_movement = movementRepository.save(movement);

        return movimentToResponse(saved_movement);
    }

    /**
     * Withdrawal amount from user's account verifying its balance and operation type.
     * @param user retrieved
     * @param request credentials needed for the transation
     * @return movement
     */
    public MovementResponse withdrawal(ActioUser user, MovementRequest request) {

        Account account = getActiveAccount(user);

        if (account.getCurrentBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        MovementType rescueType = movementTypeRepository.findByTypeDescription("RESCUE")
                .orElseThrow(() -> new RuntimeException("MovementType RESCUE not found"));

        Movement movement = Movement.builder()
                .account(account)
                .amount(request.getAmount())
                .movementType(rescueType)
                .movementDateTime(LocalDateTime.now())
                .build();
        Movement saved_withdrawal = movementRepository.save(movement);

        return movimentToResponse(saved_withdrawal);
    }

    /**
     * Retrieves user's history of movements
     * @param user to be found
     * @return a list of movements
     */
    public List<MovementResponse> getHistory(ActioUser user) {
        Account account = getActiveAccount(user);
        return movementRepository
                .findByAccount(account)
                .stream()
                .map(this::movimentToResponse)
                .collect(Collectors.toList());
    }


    private MovementResponse movimentToResponse(Movement movement){
        return  MovementResponse.builder()
                .id(movement.getId())
                .amount(movement.getAmount())
                .type(movement.getMovementType().getTypeDescription())
                .dateTime(movement.getMovementDateTime())
                .build();
    }

    /**
     * Retrieves last 200(demo maximum amount) movements from user
     * @return a list of movements
     */
    public List<Movement> getLast200Movements() {
        Pageable limit = PageRequest.of(0, 200, Sort.by(Sort.Direction.DESC, "movementDateTime"));
        return movementRepository.findAll(limit).getContent();
    }

    /**
     * Retrieves Account Status and verify if its ACTIVE
     * @param user to be checked
     * @return active account
     */
    private Account getActiveAccount(ActioUser user) {
        Account account = accountRepository.findByActioUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found for user " + user.getEmail()));

        AccountStatus activeStatus = accountStatusRepository.findByStatusDescription("ACTIVE")
                .orElseThrow(() -> new RuntimeException("AccountStatus ACTIVE not found"));

        if (!account.getStatus().equals(activeStatus)) {
            throw new RuntimeException("Account is not ACTIVE");
        }
        return account;
    }

    private MovementType getMovementTypeByDescription(String description) {
        return movementTypeRepository.findByTypeDescription(description)
                .orElseThrow(() -> new RuntimeException("MovementType '" + description + "' not found"));
    }

    public void save(Movement movement) {
        Movement saved_movement = movementRepository.save(movement);
    }
}
