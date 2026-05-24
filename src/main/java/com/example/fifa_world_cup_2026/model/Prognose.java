package com.example.fifa_world_cup_2026.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prognoses",
uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "match_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prognose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    private Integer predictedScoreA;
    private Integer predictedScoreB;

    @Column(nullable = false)
    private Integer points = 0;

}
