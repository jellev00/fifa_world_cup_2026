package com.example.fifa_world_cup_2026.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class StadiumCapacityService {

    private static final Map<String, Integer> CAPACITIES = Map.ofEntries(
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

    public Integer getCapacityForStadium(String stadiumName) {
        if (stadiumName == null || stadiumName.isBlank()) {
            return null;
        }
        return CAPACITIES.entrySet().stream()
                .filter(e -> stadiumName.toLowerCase()
                        .contains(e.getKey().toLowerCase()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

}
