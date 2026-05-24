package com.example.fifa_world_cup_2026.dto.request;

import com.example.fifa_world_cup_2026.validator.GeldigeChecksum;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@GeldigeChecksum
public class MatchRequest {

    @NotBlank(message = "{match.countryA.notblank}")
    private String countryA;

    @NotBlank(message = "{match.countryB.notblank}")
    private String countryB;

    @NotNull(message = "{match.datetime.notnull}")
    private LocalDateTime matchDateTime;

    private String city;
    private String stadium;

    @Pattern(regexp = "\\d{4}", message = "{match.stadiumcode.pattern}")
    private String stadiumCode;

    private Integer checksum;

}
