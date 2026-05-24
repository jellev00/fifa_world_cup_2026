package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.model.Match;
import com.example.fifa_world_cup_2026.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MatchRestController {

    private final MatchService matchService;

    /**
     * Voorbeeldrequest:
     *   GET /api/matches
     *   GET /api/matches/{id}
     *   GET /api/matches?datum=2026-06-15
     */

    @GetMapping("/matches")
    public ResponseEntity<List<Map<String, Object>>> getMatches(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum) {
        List<Match> matches;

        if (datum != null) {
            LocalDateTime start = datum.atStartOfDay();
            LocalDateTime end = datum.atTime(LocalTime.MAX);
            matches = matchService.getMatchesByDate(start, end);
        } else {
            matches = matchService.getAllMatchesSorted();
        }

        List<Map<String, Object>> result = matches.stream()
                .map(this::toMap)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<Map<String, Object>> getMatch(@PathVariable Long id) {
        Match match = matchService.getById(id);
        return ResponseEntity.ok(toMap(match));
    }

    private Map<String, Object> toMap(Match match) {
        return Map.of(
                "id",            match.getId(),
                "countryA",      match.getCountryA(),
                "countryB",      match.getCountryB(),
                "matchDateTime", match.getMatchDateTime().toString(),
                "city",          match.getCity() != null ? match.getCity() : "",
                "stadium",       match.getStadium() != null ? match.getStadium() : "",
                "stadiumCode",   match.getStadiumCode() != null ? match.getStadiumCode() : "",
                "played",        match.isPlayed(),
                "officialScoreA", match.getOfficialScoreA() != null ? match.getOfficialScoreA() : -1,
                "officialScoreB", match.getOfficialScoreB() != null ? match.getOfficialScoreB() : -1
        );
    }

}
