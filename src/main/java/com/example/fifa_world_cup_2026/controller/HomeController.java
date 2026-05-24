package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MatchRepository matchRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("matches", matchRepository.findAllByOrderByMatchDateTimeAsc());
        return "index";
    }

    @GetMapping("/error/403")
    public String forbidden(Model model) {
        model.addAttribute("errorTitle", "Geen toegang");
        model.addAttribute("errorMessage",
                "Je hebt geen toestemming om deze pagina te bekijken.");
        model.addAttribute("errorCode", "403");
        return "error/403";
    }

    @GetMapping("/error/404")
    public String notFound(Model model) {
        model.addAttribute("errorTitle", "Pagina niet gevonden");
        model.addAttribute("errorMessage",
                "De pagina die je zoekt bestaat niet.");
        model.addAttribute("errorCode", "404");
        return "error/404";
    }

}
