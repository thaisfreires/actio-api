package com.actio.actio_api.validation;

import lombok.Getter;

import java.util.Map;

/**
 * Custom runtime exception used to represent validation errors on specific request fields.
 *
 */
@Getter
public class FieldValidationException extends RuntimeException {
    private final Map<String, String> errors;

    /**
     * Constructs a new exception with a map of field errors.
     *
     * @param errors a map of field names and their associated validation messages
     */
    public FieldValidationException(Map<String, String> errors) {
        super("Field validation error");
        this.errors = errors;
    }

}