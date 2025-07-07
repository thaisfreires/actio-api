package com.actio.actio_api.validation;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that intercepts and formats validation and persistence-related errors.
 *
 * This class is annotated with {@link RestControllerAdvice}, enabling centralized exception handling
 * across all controllers in the application. It transforms exceptions into structured JSON responses
 * suitable for REST clients.
 */
@RestControllerAdvice
public class ValidationExceptionHandler {

    /**
     * Handles validation exceptions triggered by {@code @Valid} or {@code @Validated} annotations
     * when standard bean validation fails.
     *
     * @param ex the thrown {@link MethodArgumentNotValidException}
     * @return a 400 Bad Request response containing field-specific error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    /**
     * Handles custom business-rule validation exceptions such as duplicated email or NIF.
     *
     * @param ex the thrown {@link FieldValidationException}
     * @return a 400 Bad Request response containing field-specific error details
     */
    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<Map<String, String>> handleFieldValidation(FieldValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getErrors());
    }

    /**
     * Handles exceptions caused by database integrity constraint violations such as unique key conflicts.
     *
     * @param ex the thrown {@link DataIntegrityViolationException}
     * @return a 409 Conflict response with technical error information
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String detail = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "Database integrity violation";

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Integrity violation", "details", detail));
    }
}