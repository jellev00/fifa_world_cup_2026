package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.dto.response.ScoreboardEntry;
import com.example.fifa_world_cup_2026.model.Team;
import com.example.fifa_world_cup_2026.model.User;
import com.example.fifa_world_cup_2026.repository.TeamRepository;
import com.example.fifa_world_cup_2026.repository.UserRepository;
import com.example.fifa_world_cup_2026.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ScoreboardController {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ScoreService scoreService;

    // ─── Privé team scoreboard ────────────────────────────────────
    @GetMapping("/team/{teamId}/scoreboard")
    public String teamScoreboard(
            @PathVariable Long teamId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team niet gevonden"));

        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        // Controleer lidmaatschap
        boolean isMember = team.getMembers().stream()
                .anyMatch(m -> m.getId().equals(currentUser.getId()));
        if (!isMember) {
            model.addAttribute("errorMessage",
                    "Je hebt geen toegang tot dit scoreboard.");
            return "error";
        }

        // Bouw gesorteerde lijst
        AtomicInteger rank = new AtomicInteger(1);
        List<ScoreboardEntry> entries = team.getMembers().stream()
                .map(member -> ScoreboardEntry.builder()
                        .userId(member.getId())
                        .username(member.getUsername())
                        .totalPoints(scoreService.getTotalPointsForUser(member))
                        .build())
                .sorted(Comparator.comparingInt(ScoreboardEntry::getTotalPoints).reversed())
                .peek(e -> e.setRank(rank.getAndIncrement()))
                .collect(Collectors.toList());

        model.addAttribute("team", team);
        model.addAttribute("entries", entries);
        model.addAttribute("currentUserId", currentUser.getId());
        return "scoreboard/team";
    }

    // ─── Publieke Top-10 ──────────────────────────────────────────
    @GetMapping("/top10")
    public String top10(Model model) {
        List<Team> teams = teamRepository.findAll();

        AtomicInteger rank = new AtomicInteger(1);
        List<Map<String, Object>> top10 = teams.stream()
                .map(team -> {
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("team", team);
                    entry.put("totalScore", scoreService.getTotalPointsForTeam(team));
                    entry.put("memberCount", team.getMembers().size());
                    return entry;
                })
                .sorted((a, b) ->
                        Integer.compare((int) b.get("totalScore"), (int) a.get("totalScore")))
                .limit(10)
                .peek(e -> e.put("rank", rank.getAndIncrement()))
                .collect(Collectors.toList());

        model.addAttribute("top10", top10);
        return "scoreboard/top10";
    }

}
