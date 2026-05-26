package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.dto.request.PrognoseRequest;
import com.example.fifa_world_cup_2026.model.*;
import com.example.fifa_world_cup_2026.repository.UserRepository;
import com.example.fifa_world_cup_2026.service.*;
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

    private final MatchService matchService;
    private final PrognoseService prognoseService;
    private final UserRepository userRepository;
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
