package com.example.fifa_world_cup_2026.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreboardEntry {

    private int rank;
    private String username;
    private int totalPoints;
    private Long userId;

}
