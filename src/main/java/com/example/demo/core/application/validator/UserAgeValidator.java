package com.example.demo.core.application.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserAgeValidator implements ConstraintValidator<UserAgeConstraint, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value % 2 == 0;
    }
}
