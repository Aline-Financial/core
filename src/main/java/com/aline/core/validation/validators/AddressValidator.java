package com.aline.core.validation.validators;

import com.aline.core.validation.annotations.Address;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<Address, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches("^([0-9]+([a-zA-Z]+)?)\\s(.*)(\\s)([a-zA-Z]+)(\\.)?(\\s(#?(\\w+))|([A-Za-z]+\\.?(\\w+)))?$");
    }
}
