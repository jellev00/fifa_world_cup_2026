package com.example.fifa_world_cup_2026.service;

import com.example.fifa_world_cup_2026.model.Match;
import com.example.fifa_world_cup_2026.model.Prognose;
import com.example.fifa_world_cup_2026.model.Team;
import com.example.fifa_world_cup_2026.model.User;
import com.example.fifa_world_cup_2026.repository.PrognoseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final PrognoseRepository prognoseRepository;

    // Puntenwaardes uit messages.properties
    @Value("${score.exact.points}")
    private int exactPoints;

    @Value("${score.winner.points}")
    private int winnerPoints;

    @Value("${score.bonus.exclusive.exact}")
    private int bonusExclusiveExact;

    @Value("${score.bonus.exclusive.winner}")
    private int bonusExclusiveWinner;

    public void berekenPunten(Match match) {
        List<Prognose> prognoses = prognoseRepository.findByMatch(match);

        int officieleA = match.getOfficialScoreA();
        int officieleB = match.getOfficialScoreB();

        String officieleWinnaar = bepaalWinnaar(officieleA, officieleB);

        long aantalExact = prognoses.stream()
                .filter(p -> p.getPredictedScoreA() != null && p.getPredictedScoreB() != null)
                .filter(p -> p.getPredictedScoreA() == officieleA &&
                        p.getPredictedScoreB() == officieleB)
                .count();

        long aantalJuisteWinnaar = prognoses.stream()
                .filter(p -> p.getPredictedScoreA() != null && p.getPredictedScoreB() != null)
                .filter(p -> bepaalWinnaar(p.getPredictedScoreA(), p.getPredictedScoreB()).equals(officieleWinnaar))
                .count();

        for (Prognose prognose : prognoses) {
            if (prognose.getPredictedScoreA() == null ||
            prognose.getPredictedScoreB() == null) {
                prognose.setPoints(0);
                prognoseRepository.save(prognose);
                continue;
            }

            int punten = 0;
            int predA = prognose.getPredictedScoreA();
            int predB = prognose.getPredictedScoreB();
            String predWinnaar = bepaalWinnaar(predA, predB);

            boolean exactGoed = (predA == officieleA && predB == officieleB);
            boolean winnaarGoed = predWinnaar.equals(officieleWinnaar);

            if (exactGoed) {
                punten += exactPoints;
                if (aantalJuisteWinnaar == 1) {
                    punten += bonusExclusiveExact;
                }
            } else if (winnaarGoed) {
                punten += winnerPoints;
                if (aantalJuisteWinnaar == 1) {
                    punten += bonusExclusiveWinner;
                }
            }

            prognose.setPoints(punten);
            prognoseRepository.save(prognose);
        }
    }

    private String bepaalWinnaar(int scoreA, int scoreB) {
        if (scoreA > scoreB) return "A";
        if (scoreB > scoreA) return "B";
        return "GELIJK";
    }

    public int getTotalPointsForUser(User user) {
        return prognoseRepository.findByUser(user)
                .stream()
                .mapToInt(p -> p.getPoints() != null ? p.getPoints() : 0)
                .sum();
    }

    public int getTotalPointsForTeam(Team team) {
        return team.getMembers()
                .stream()
                .mapToInt(this::getTotalPointsForUser)
                .sum();
    }

}
