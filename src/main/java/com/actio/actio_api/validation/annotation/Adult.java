package com.actio.actio_api.validation.annotation;

import com.actio.actio_api.validation.validator.AdultValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation used to ensure that a user is at least 18 years old.
 *
 */
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
@Documented
public @interface Adult {
    String message() default "Registration restricted to people over 18 years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
