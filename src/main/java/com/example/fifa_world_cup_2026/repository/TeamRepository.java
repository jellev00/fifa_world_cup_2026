package com.example.fifa_world_cup_2026.repository;

import com.example.fifa_world_cup_2026.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByInviteCode(String inviteCode);
    boolean existsByName(String name);
    Optional<Team> findByName(String name);

}
