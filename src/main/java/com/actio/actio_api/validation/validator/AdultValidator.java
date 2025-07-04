package com.actio.actio_api.validation.validator;


import com.actio.actio_api.validation.annotation.Adult;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class AdultValidator implements ConstraintValidator<Adult, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) return true;

        LocalDateTime today = LocalDateTime.now();
        return birthDate.plusYears(18).isBefore(today);
    }
}


