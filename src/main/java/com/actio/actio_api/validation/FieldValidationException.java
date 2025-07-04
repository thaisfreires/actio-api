package com.actio.actio_api.validation;

import java.util.Map;

public class FieldValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public FieldValidationException(Map<String, String> errors) {
        super("Erro de validação nos campos");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}