package com.progressSoft.clusteredDataWarehouse.utilities;

import com.progressSoft.clusteredDataWarehouse.annotation.IsCurrency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;

import java.util.Currency;
import java.util.Objects;

@Log4j2
public class isCurrencyValidator implements ConstraintValidator<IsCurrency, Object> {

    private String message;

    @Override
    public void initialize(final IsCurrency constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        log.debug("validating currency in spring custom validator  ");
        if (Objects.isNull(o)){
            this.message = "Currency cannot be null";
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return  false;
        }
        String stringVal = String.valueOf(o);
        boolean isValid = isISOCodeValid(stringVal);
        if (!isValid) {
            this.message = "Currency is invalid";
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
        return isValid;
    }

    private boolean isISOCodeValid(String currency) {
        return Currency.getAvailableCurrencies().stream().anyMatch(c -> c.getCurrencyCode().equals(currency));
    }
}
