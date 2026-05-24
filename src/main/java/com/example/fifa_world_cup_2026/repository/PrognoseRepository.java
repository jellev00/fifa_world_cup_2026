package com.example.fifa_world_cup_2026.repository;

import com.example.fifa_world_cup_2026.model.Match;
import com.example.fifa_world_cup_2026.model.Prognose;
import com.example.fifa_world_cup_2026.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PrognoseRepository extends JpaRepository<Prognose, Long> {

    Optional<Prognose> findByUserAndMatch(User user, Match match);
    List<Prognose> findByMatch(Match match);
    List<Prognose> findByUser(User user);

}
