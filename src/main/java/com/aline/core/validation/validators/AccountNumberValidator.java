package com.aline.core.validation.validators;

import com.aline.core.validation.annotations.AccountNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountNumberValidator implements ConstraintValidator<AccountNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        return value.matches("^[0-9]{8,10}");
    }
}
