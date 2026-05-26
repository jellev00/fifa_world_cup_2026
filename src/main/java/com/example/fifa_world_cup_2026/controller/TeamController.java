package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.dto.request.TeamRequest;
import com.example.fifa_world_cup_2026.model.Team;
import com.example.fifa_world_cup_2026.model.User;
import com.example.fifa_world_cup_2026.repository.TeamRepository;
import com.example.fifa_world_cup_2026.repository.UserRepository;
import com.example.fifa_world_cup_2026.service.ScoreService;
import com.example.fifa_world_cup_2026.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ScoreService scoreService;

    private User getCurrentUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("teamRequest", new TeamRequest());
        return "team/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("teamRequest")  TeamRequest request,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "team/create";
        }

        try {
            User user = getCurrentUser(userDetails);
            Team team = teamService.createTeam(request.getName(), user);
            return "redirect:/team/" + team.getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "team/create";
        }
    }

    @GetMapping("/join")
    public String joinPage() {
        return "team/join";
    }

    @PostMapping("/join")
    public String join(
            @RequestParam("inviteCode") String inviteCode,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        try {
            User user = getCurrentUser(userDetails);
            Team team = teamService.joinTeam(inviteCode.trim(), user);
            return "redirect:/team/" + team.getId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "team/join";
        }
    }

    @GetMapping("/{id}")
    public String teamDetail(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        User user = getCurrentUser(userDetails);
        Team team = teamService.getTeamById(id);

        boolean isMember = team.getMembers().stream()
                .anyMatch(m -> m.getId().equals(user.getId()));

        if (!isMember) {
            model.addAttribute("errorMessage", "Je bent geen lid van dit team.");
            return "error";
        }

        // Score per lid berekenen
        Map<Long, Integer> userScores = new HashMap<>();
        for (User member : team.getMembers()) {
            userScores.put(member.getId(), scoreService.getTotalPointsForUser(member));
        }

        int totalTeamScore = scoreService.getTotalPointsForTeam(team);

        model.addAttribute("team", team);
        model.addAttribute("userScores", userScores);
        model.addAttribute("totalTeamScore", totalTeamScore);
        model.addAttribute("isOwner", team.getOwner().getId().equals(user.getId()));
        model.addAttribute("currentUser", user);
        return "team/detail";
    }

    @PostMapping("/{id}/remove-member/{meberId}")
    public String removeMember(
            @PathVariable Long id,
            @PathVariable Long memberId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        try {
            User user = getCurrentUser(userDetails);
            teamService.removeMember(id, memberId, user);
            return "redirect:/team/" + id;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error/error";
        }
    }

    @GetMapping("/mijn-teams")
    public String mijnTeams(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        User user = getCurrentUser(userDetails);

        List<Team> teams = teamRepository.findAll().stream()
                .filter(t -> t.getMembers().stream()
                        .anyMatch(m -> m.getId().equals(user.getId())))
                .toList();

        // Score per team berekenen
        Map<Long, Integer> teamScores = new HashMap<>();
        for (Team team : teams) {
            teamScores.put(team.getId(), scoreService.getTotalPointsForTeam(team));
        }

        // Score per user berekenen (voor alle leden van alle teams)
        Map<Long, Integer> userScores = new HashMap<>();
        for (Team team : teams) {
            for (User member : team.getMembers()) {
                userScores.put(member.getId(), scoreService.getTotalPointsForUser(member));
            }
        }

        model.addAttribute("teams", teams);
        model.addAttribute("teamScores", teamScores);
        model.addAttribute("userScores", userScores);
        model.addAttribute("currentUser", user);
        return "team/mijn-teams";
    }

}
