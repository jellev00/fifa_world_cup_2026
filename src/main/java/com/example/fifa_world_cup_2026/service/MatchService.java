package com.example.fifa_world_cup_2026.service;

import com.example.fifa_world_cup_2026.dto.request.MatchRequest;
import com.example.fifa_world_cup_2026.exception.MatchNotFoundException;
import com.example.fifa_world_cup_2026.model.Match;
import com.example.fifa_world_cup_2026.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private static final LocalDateTime WK_START = LocalDateTime.of(2026, 5, 20, 0, 0);
    private static final LocalDateTime WK_END   = LocalDateTime.of(2026, 7, 19, 23, 59);

    public List<Match> getAllMatchesSorted() {
        return matchRepository.findAllByOrderByMatchDateTimeAsc();
    }

    public Match getById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));
    }

    public Match createMatch(MatchRequest request) {
        validateMatch(request, null);

        Match match = Match.builder()
                .countryA(request.getCountryA())
                .countryB(request.getCountryB())
                .matchDateTime(request.getMatchDateTime())
                .city(request.getCity())
                .stadium(request.getStadium())
                .stadiumCode(request.getStadiumCode())
                .checksum(request.getChecksum())
                .played(false)
                .build();

        return matchRepository.save(match);
    }

    public Match updateMatch(Long id, MatchRequest request) {
        Match match = getById(id);
        validateMatch(request, id);

        match.setCountryA(request.getCountryA());
        match.setCountryB(request.getCountryB());
        match.setMatchDateTime(request.getMatchDateTime());
        match.setCity(request.getCity());
        match.setStadium(request.getStadium());
        match.setStadiumCode(request.getStadiumCode());
        match.setChecksum(request.getChecksum());

        return matchRepository.save(match);
    }

    private void validateMatch(MatchRequest request, Long excludeId) {
        // Land A != Land B
        if (request.getCountryA() != null &&
                request.getCountryA().equalsIgnoreCase(request.getCountryB())) {
            throw new IllegalArgumentException("Land A en Land B mogen niet hetzelfde zijn.");
        }

        // Datum binnen WK-periode
        if (request.getMatchDateTime() != null) {
            if (request.getMatchDateTime().isBefore(WK_START) ||
                    request.getMatchDateTime().isAfter(WK_END)) {
                throw new IllegalArgumentException(
                        "Datum moet binnen de WK-periode vallen (11 juni – 19 juli 2026).");
            }
        }

        // Geen twee wedstrijden op dezelfde locatie op hetzelfde moment
        if (request.getStadium() != null && request.getMatchDateTime() != null) {
            boolean conflict = matchRepository
                    .findAllByOrderByMatchDateTimeAsc()
                    .stream()
                    .filter(m -> excludeId == null || !m.getId().equals(excludeId))
                    .anyMatch(m ->
                            m.getStadium() != null &&
                                    m.getStadium().equalsIgnoreCase(request.getStadium()) &&
                                    m.getMatchDateTime().equals(request.getMatchDateTime())
                    );
            if (conflict) {
                throw new IllegalArgumentException(
                        "Er staat al een wedstrijd gepland op dit moment in dit stadion.");
            }
        }
    }

    public List<Match> getMatchesByDate(LocalDateTime start, LocalDateTime end) {
        return matchRepository.findByMatchDateTimeBetweenOrderByMatchDateTimeAsc(start, end);
    }

    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

}
