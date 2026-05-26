package com.example.fifa_world_cup_2026.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StadiumCapacityService {

    private final WebClient webClient;

    public Integer getCapacityForStadium(String stadiumName) {
        if (stadiumName == null || stadiumName.isBlank()) {
            return null;
        }

        try {
            Map<?, ?> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/stadiums")
                            .queryParam("name", stadiumName)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(5))
                    .onErrorResume(ex -> {
                        log.warn("Stadium API niet bereikbaar voor '{}': {}", stadiumName, ex.getMessage());
                        return Mono.empty();
                    })
                    .block();

            if (response != null && response.containsKey("capacity")) {
                return (Integer) response.get("capacity");
            }
        } catch (Exception e) {
            log.warn("Fout bij ophalen stadioncapaciteit: {}", e.getMessage());
        }

        return getLocalCapacity(stadiumName);
    }

    private Integer getLocalCapacity(String stadiumName) {
        Map<String, Integer> capacities = Map.ofEntries(
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

        return capacities.entrySet().stream()
                .filter(e -> stadiumName.toLowerCase()
                        .contains(e.getKey().toLowerCase()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

}
