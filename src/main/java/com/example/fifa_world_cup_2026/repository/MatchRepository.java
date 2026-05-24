package com.example.fifa_world_cup_2026.repository;

import com.example.fifa_world_cup_2026.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findAllByOrderByMatchDateTimeAsc();
    List<Match> findByMatchDateTimeBetweenOrderByMatchDateTimeAsc(LocalDateTime start, LocalDateTime end);
    boolean existsByStadiumAndMatchDateTime(String stadium, LocalDateTime matchDateTime);

}
