package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.dto.request.MatchRequest;
import com.example.fifa_world_cup_2026.dto.request.UitslagRequest;
import com.example.fifa_world_cup_2026.model.Match;
import com.example.fifa_world_cup_2026.service.MatchService;
import com.example.fifa_world_cup_2026.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MatchService matchService;
    private final ScoreService scoreService;

    // ─── Overzicht ────────────────────────────────────────────────
    @GetMapping("/matches")
    public String matchOverzicht(Model model) {
        model.addAttribute("matches", matchService.getAllMatchesSorted());
        return "admin/matches";
    }

    // ─── Aanmaken ─────────────────────────────────────────────────
    @GetMapping("/matches/new")
    public String newMatchForm(Model model) {
        model.addAttribute("matchRequest", new MatchRequest());
        model.addAttribute("editMode", false);
        return "admin/match-form";
    }

    @PostMapping("/matches/new")
    public String createMatch(
            @Valid @ModelAttribute("matchRequest") MatchRequest request,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", false);
            return "admin/match-form";
        }

        try {
            matchService.createMatch(request);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Wedstrijd succesvol toegevoegd.");
            return "redirect:/admin/matches";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("editMode", false);
            return "admin/match-form";
        }
    }

    // ─── Bewerken ─────────────────────────────────────────────────
    @GetMapping("/matches/{id}/edit")
    public String editMatchForm(@PathVariable Long id, Model model) {
        Match match = matchService.getById(id);

        // Vul het formulier in met bestaande waarden
        MatchRequest request = new MatchRequest();
        request.setCountryA(match.getCountryA());
        request.setCountryB(match.getCountryB());
        request.setMatchDateTime(match.getMatchDateTime());
        request.setCity(match.getCity());
        request.setStadium(match.getStadium());
        request.setStadiumCode(match.getStadiumCode());
        request.setChecksum(match.getChecksum());

        model.addAttribute("matchRequest", request);
        model.addAttribute("matchId", id);
        model.addAttribute("editMode", true);
        return "admin/match-form";
    }

    @PostMapping("/matches/{id}/edit")
    public String updateMatch(
            @PathVariable Long id,
            @Valid @ModelAttribute("matchRequest") MatchRequest request,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("matchId", id);
            model.addAttribute("editMode", true);
            return "admin/match-form";
        }

        try {
            matchService.updateMatch(id, request);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Wedstrijd succesvol bijgewerkt.");
            return "redirect:/admin/matches";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("matchId", id);
            model.addAttribute("editMode", true);
            return "admin/match-form";
        }
    }

    // ─── Uitslag ingeven ──────────────────────────────────────────
    @GetMapping("/matches/{id}/uitslag")
    public String uitslagForm(@PathVariable Long id, Model model) {
        Match match = matchService.getById(id);
        UitslagRequest request = new UitslagRequest();

        // Pre-fill als er al een uitslag is
        if (match.getOfficialScoreA() != null) {
            request.setScoreA(match.getOfficialScoreA());
            request.setScoreB(match.getOfficialScoreB());
        }

        model.addAttribute("match", match);
        model.addAttribute("uitslagRequest", request);
        return "admin/uitslag-form";
    }

    @PostMapping("/matches/{id}/uitslag")
    public String saveUitslag(
            @PathVariable Long id,
            @Valid @ModelAttribute("uitslagRequest") UitslagRequest request,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        Match match = matchService.getById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("match", match);
            return "admin/uitslag-form";
        }

        // Sla uitslag op
        match.setOfficialScoreA(request.getScoreA());
        match.setOfficialScoreB(request.getScoreB());
        match.setPlayed(true);

        // Sla op via repository (via service)
        matchService.saveMatch(match);

        // Bereken punten voor alle prognoses van deze wedstrijd
        scoreService.berekenPunten(match);

        redirectAttributes.addFlashAttribute("successMessage",
                "Uitslag opgeslagen. Punten zijn berekend!");
        return "redirect:/admin/matches";
    }

}
