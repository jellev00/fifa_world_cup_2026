package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.dto.request.PrognoseRequest;
import com.example.fifa_world_cup_2026.model.Match;
import com.example.fifa_world_cup_2026.model.Prognose;
import com.example.fifa_world_cup_2026.model.User;
import com.example.fifa_world_cup_2026.repository.PrognoseRepository;
import com.example.fifa_world_cup_2026.repository.UserRepository;
import com.example.fifa_world_cup_2026.service.MatchService;
import com.example.fifa_world_cup_2026.service.PrognoseService;
import com.example.fifa_world_cup_2026.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/prognose")
@RequiredArgsConstructor
public class PrognoseController {

    private final MatchService matchService;
    private final PrognoseRepository prognoseRepository;
    private final UserService userService;
    private final PrognoseService prognoseService;
    private final UserRepository userRepository;

    @PostMapping("/match/{matchId}")
    public String savePrognose(
            @PathVariable Long matchId,
            @Valid @ModelAttribute("prognoseRequest")PrognoseRequest request,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes,
            Model model) {
        Match match = matchService.getById(matchId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("match", match);
            model.addAttribute("prognoseOpen", prognoseService.isPrognoseOpen(match));
            return "match/detail";
        }

        try {
            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow();
            prognoseService.saveOrUpdate(user, match, request);
            redirectAttributes.addFlashAttribute("successMessage", "Je voorspelling is opgeslagen!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/match/" + matchId;
    }

    @GetMapping("/overzicht")
    public String overzicht(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow();

        List<Match> matches = matchService.getAllMatchesSorted();
        Map<Long, Prognose> prognoseMap = new HashMap<>();

        for (Match match : matches) {
            prognoseService.findByUserAndMatch(user, match)
                    .ifPresent(p -> prognoseMap.put(match.getId(), p));
        }

        model.addAttribute("matches", matches);
        model.addAttribute("prognoseMap", prognoseMap);
        return "prognose/overzicht";
    }

}
