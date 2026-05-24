package com.example.fifa_world_cup_2026.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String countryA;

    @Column(nullable = false, length = 100)
    private String countryB;

    @Column(nullable = false)
    private LocalDateTime matchDateTime;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String stadium;

    @Column(length = 4)
    private String stadiumCode;

    private Integer checksum;

    private Integer officialScoreA;
    private Integer officialScoreB;

    @Column(nullable = false)
    private boolean played = false;

}
