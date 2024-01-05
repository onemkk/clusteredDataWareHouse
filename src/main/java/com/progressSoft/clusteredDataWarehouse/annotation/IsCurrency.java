package com.progressSoft.clusteredDataWarehouse.annotation;

import com.progressSoft.clusteredDataWarehouse.utilities.isCurrencyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = isCurrencyValidator.class)
@Documented
public @interface IsCurrency {
    String message() default "Currency is invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
