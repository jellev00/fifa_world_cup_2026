package com.example.fifa_world_cup_2026.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UitslagRequest {

    @NotNull(message = "{uitslag.scoreA.notnull}")
    @Min(value = 0, message = "{uitslag.scoreA.min}")
    private Integer scoreA;

    @NotNull(message = "{uitslag.scoreB.notnull}")
    @Min(value = 0, message = "{uitslag.scoreB.min}")
    private Integer scoreB;

}
