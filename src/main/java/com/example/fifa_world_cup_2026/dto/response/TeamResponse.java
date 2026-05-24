package com.example.fifa_world_cup_2026.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {

    private Long id;
    private String name;
    private String inviteCode;
    private String ownerUsername;
    private List<String> memberUsernames;
    private int totalScore;
    private int memberCount;

}
