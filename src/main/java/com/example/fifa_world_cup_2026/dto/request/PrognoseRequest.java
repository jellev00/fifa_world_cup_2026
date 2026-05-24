package com.example.fifa_world_cup_2026.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PrognoseRequest {

    @NotNull(message = "{prognose.scoreA.notnull}")
    @Min(value = 0, message = "{prognose.scoreA.min}")
    private Integer predictedScoreA;

    @NotNull(message = "{prognose.scoreB.notnull}")
    @Min(value = 0, message = "{prognose.scoreB.min}")
    private Integer predictedScoreB;

}
