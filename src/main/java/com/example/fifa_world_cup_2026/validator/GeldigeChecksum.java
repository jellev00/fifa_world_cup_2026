package com.example.fifa_world_cup_2026.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ChecksumValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeldigeChecksum {

    String message() default "{match.checksum.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
