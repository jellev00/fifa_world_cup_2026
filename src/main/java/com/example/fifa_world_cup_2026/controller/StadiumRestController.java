package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.service.StadiumCapacityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StadiumRestController {

    private final StadiumCapacityService stadiumCapacityService;

    /**
     * Voorbeeldrequest:
     * GET /api/stadiums
     * GET /api/stadiums/capacity?name=MetLife Stadium
     */

    @GetMapping("/stadiums/capacity")
    public ResponseEntity<Map<String, Object>> getCapacity(@RequestParam String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Stadiumnaam is verplicht")
            );
        }

        Integer capacity = stadiumCapacityService.getCapacityForStadium(name);

        if (capacity == null) {
            return ResponseEntity.status(404).body(Map.of(
                    "stadium", name,
                    "error", "Capaciteit niet gevonden voor dit stadion."
            ));
        }

        return ResponseEntity.ok(Map.of(
                "stadium", name,
                "capacity", capacity
        ));
    }

    @GetMapping("/stadiums")
    public ResponseEntity<Map<String, Object>> getAllStadiums() {
        Map<String, Integer> stadiums = Map.ofEntries(
                Map.entry("MetLife Stadium",         82500),
                Map.entry("AT&T Stadium",            80000),
                Map.entry("SoFi Stadium",            70240),
                Map.entry("Rose Bowl",               92000),
                Map.entry("Levi's Stadium",          68500),
                Map.entry("Arrowhead Stadium",       76416),
                Map.entry("Estadio Azteca",          87523),
                Map.entry("Estadio Akron",           49850),
                Map.entry("BC Place",                54500),
                Map.entry("Empower Field",           76125),
                Map.entry("Gillette Stadium",        65000),
                Map.entry("Lincoln Financial Field", 69176),
                Map.entry("NRG Stadium",             72220),
                Map.entry("Mercedes-Benz Stadium",   71000),
                Map.entry("Hard Rock Stadium",       65326),
                Map.entry("Camping World Stadium",   45000)
        );

        return ResponseEntity.ok(Map.of(
                "count", stadiums.size(),
                "stadiums", stadiums
        ));
    }

}
