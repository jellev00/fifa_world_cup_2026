package com.example.fifa_world_cup_2026.service;

import com.example.fifa_world_cup_2026.exception.TeamNotFoundException;
import com.example.fifa_world_cup_2026.model.Team;
import com.example.fifa_world_cup_2026.model.User;
import com.example.fifa_world_cup_2026.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team createTeam(String name, User owner) {
        if (teamRepository.existsByName(name)) {
            throw new IllegalArgumentException("Teamnaam is al in gebruik.");
        }

        Team team = Team.builder()
                .name(name)
                .inviteCode(generateInviteCode())
                .owner(owner)
                .members(new HashSet<>(Set.of(owner)))
                .build();

        return teamRepository.save(team);
    }

    public Team joinTeam(String inviteCode, User user) {
        Team team = teamRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new TeamNotFoundException("Geen team gevonden met deze invitecode."));

        team.getMembers().add(user);
        return teamRepository.save(team);
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }

    public Team regenerateInviteCode(Long teamId, User requestingUser) {
        Team team = getTeamById(teamId);
        if (!team.getOwner().getId().equals(requestingUser.getId())) {
            throw new SecurityException("Enkel de eigenaar kan de invitecode hergenereren.");
        }
        team.setInviteCode(generateInviteCode());
        return teamRepository.save(team);
    }

    public void removeMember(Long teamId, Long memberId, User requestingUser) {
        Team team = getTeamById(teamId);
        if (!team.getOwner().getId().equals(requestingUser.getId())) {
            throw new SecurityException("Enkel de eigenaar kan leden verwijderen.");
        }
        if (memberId.equals(requestingUser.getId())) {
            throw new IllegalArgumentException("De eigenaar kan zichzelf niet verwijderen.");
        }
        team.getMembers().removeIf(m -> m.getId().equals(memberId));
        teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public String generateInviteCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

}
