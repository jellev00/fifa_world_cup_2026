package com.example.fifa_world_cup_2026.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequest {

    @NotBlank(message = "{team.name.notblank}")
    @Size(min = 2, max = 100, message = "{team.name.size}")
    private String name;

}
