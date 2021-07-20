package com.aline.core.validation.validators;

import com.aline.core.validation.annotations.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{4}$");
    }
}
