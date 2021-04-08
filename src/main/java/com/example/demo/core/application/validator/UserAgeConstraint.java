package com.example.demo.core.application.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserAgeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAgeConstraint {
    String message() default "Invalid user age";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
