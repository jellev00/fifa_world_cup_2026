package com.example.fifa_world_cup_2026.service;

import com.example.fifa_world_cup_2026.dto.request.PrognoseRequest;
import com.example.fifa_world_cup_2026.model.Match;
import com.example.fifa_world_cup_2026.model.Prognose;
import com.example.fifa_world_cup_2026.model.User;
import com.example.fifa_world_cup_2026.repository.PrognoseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrognoseService {

    private final PrognoseRepository prognoseRepository;

    public Prognose saveOrUpdate(User user, Match match, PrognoseRequest request) {
        if (LocalDateTime.now().isAfter(match.getMatchDateTime().minusHours(1))) {
            throw new IllegalStateException(
                    "Voorspellingen zijn gesloten (minder dan 1 uur voor de aftrap).");
        }

        Optional<Prognose> bestaande = prognoseRepository.findByUserAndMatch(user, match);

        Prognose prognose = bestaande.orElse(Prognose.builder()
                .user(user)
                .match(match)
                .points(0)
                .build());

        prognose.setPredictedScoreA(request.getPredictedScoreA());
        prognose.setPredictedScoreB(request.getPredictedScoreB());

        return prognoseRepository.save(prognose);
    }

    public Optional<Prognose> findByUserAndMatch(User user, Match match) {
        return prognoseRepository.findByUserAndMatch(user, match);
    }

    public boolean isPrognoseOpen(Match match) {
        return LocalDateTime.now().isBefore(match.getMatchDateTime().minusHours(1));
    }

}
