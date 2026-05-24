package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.dto.request.PrognoseRequest;
import com.example.fifa_world_cup_2026.model.Match;
import com.example.fifa_world_cup_2026.model.Prognose;
import com.example.fifa_world_cup_2026.model.User;
import com.example.fifa_world_cup_2026.repository.MatchRepository;
import com.example.fifa_world_cup_2026.repository.PrognoseRepository;
import com.example.fifa_world_cup_2026.repository.UserRepository;
import com.example.fifa_world_cup_2026.service.MatchService;
import com.example.fifa_world_cup_2026.service.PrognoseService;
import com.example.fifa_world_cup_2026.service.StadiumCapacityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchRepository matchRepository;
    private final PrognoseRepository prognoseRepository;
    private final UserRepository userRepository;
    private final MatchService matchService;
    private final PrognoseService prognoseService;
    private final StadiumCapacityService stadiumCapacityService;

    @GetMapping("/{id}")
    public String matchDetail(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        Match match = matchService.getById(id);
        model.addAttribute("match", match);
        model.addAttribute("prognoseOpen", prognoseService.isPrognoseOpen(match));

        Integer capacity = stadiumCapacityService.getCapacityForStadium(match.getStadium());
        model.addAttribute("stadiumCapacity", capacity);

        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow();
            Optional<Prognose> prognose = prognoseService.findByUserAndMatch(user, match);
            model.addAttribute("prognose", prognose.orElse(null));
            model.addAttribute("prognoseRequest", new PrognoseRequest());
        }

        return "match/detail";
    }

}
