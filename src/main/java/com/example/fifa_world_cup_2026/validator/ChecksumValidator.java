package com.example.fifa_world_cup_2026.validator;

import com.example.fifa_world_cup_2026.dto.request.MatchRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChecksumValidator implements ConstraintValidator<GeldigeChecksum, MatchRequest> {
    @Override
    public boolean isValid(MatchRequest request, ConstraintValidatorContext context) {
        // Als stadiumCode of checksum leeg is, laat andere validaties dat afhandelen
        if (request.getStadiumCode() == null || request.getStadiumCode().isBlank()) {
            return true;
        }
        if (request.getChecksum() == null) {
            return true;
        }

        try {
            int code = Integer.parseInt(request.getStadiumCode());
            int verwacht = code % 97;
            return verwacht == request.getChecksum();
        } catch (NumberFormatException e) {
            // Geen geldig getal – andere validator pakt dit op
            return true;
        }
    }
}
