package com.actio.actio_api.validation.validator;


import com.actio.actio_api.validation.annotation.Adult;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

/**
 * Validator that checks whether a given {@link LocalDateTime} value represents
 * a birthdate that qualifies the individual as an adult (18 years or older).
 *
 * This validator supports the {@link Adult} annotation
 * and is typically applied to user birthdate fields.
 */
public class AdultValidator implements ConstraintValidator<Adult, LocalDateTime> {

    /**
     * Validates that the provided birthdate is at least 18 years before the current date.
     *
     * @param birthDate the date of birth to validate
     * @param context the validator context
     * @return {@code true} if the user is at least 18 years old, or if the value is {@code null}; {@code false} otherwise
     */
    @Override
    public boolean isValid(LocalDateTime birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) return true;

        LocalDateTime today = LocalDateTime.now();
        return birthDate.plusYears(18).isBefore(today);
    }
}


