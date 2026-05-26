package com.example.fifa_world_cup_2026.validator;

import com.example.fifa_world_cup_2026.dto.request.MatchRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ChecksumValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void gelijkeChecksum_geeftGeenFout() {
        // 1234 % 97 = 70
        MatchRequest request = new MatchRequest();
        request.setCountryA("Belgium");
        request.setCountryB("France");
        request.setStadiumCode("1234");
        request.setChecksum(70);

        Set<ConstraintViolation<MatchRequest>> violations =
                validator.validate(request);

        assertTrue(violations.stream()
                .noneMatch(v -> v.getMessage().contains("checksum")));
    }

    @Test
    void verkeerdeChecksum_geeftFout() {
        // 1234 % 97 = 70, maar we geven 99 in
        MatchRequest request = new MatchRequest();
        request.setCountryA("Belgium");
        request.setCountryB("France");
        request.setStadiumCode("1234");
        request.setChecksum(99);

        Set<ConstraintViolation<MatchRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());
    }

    @Test
    void legeStadiumCode_geeftGeenChecksumFout() {
        MatchRequest request = new MatchRequest();
        request.setStadiumCode("");
        request.setChecksum(null);

        Set<ConstraintViolation<MatchRequest>> violations =
                validator.validate(request);

        assertTrue(violations.stream()
                .noneMatch(v -> v.getConstraintDescriptor()
                        .getAnnotation().annotationType()
                        .equals(com.example.fifa_world_cup_2026.validator.GeldigeChecksum.class)));
    }

    @Test
    void checksumBerekening_kloptvoor4567() {
        // 4567 % 97 = 8
        MatchRequest request = new MatchRequest();
        request.setCountryA("Spain");
        request.setCountryB("Germany");
        request.setStadiumCode("4567");
        request.setChecksum(8);

        Set<ConstraintViolation<MatchRequest>> violations =
                validator.validate(request);

        assertTrue(violations.stream()
                .noneMatch(v -> v.getMessage().contains("checksum")));
    }

}
