package com.actio.actio_api.validation.annotation;

import com.actio.actio_api.validation.validator.AdultValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultValidator.class)
@Documented
public @interface Adult {
    String message() default "Registro restrito a pessoas maiores de 18 anos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
