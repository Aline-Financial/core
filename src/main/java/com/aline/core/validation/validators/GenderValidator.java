package com.aline.core.validation.validators;

import com.aline.core.validation.annotations.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {
    @Override
    public boolean isValid(String gender, ConstraintValidatorContext context) {
        if (gender == null) {
            return true;
        }
        return gender.matches("(?i)^(Male|Female|Other|Not Specified)$");
    }
}
