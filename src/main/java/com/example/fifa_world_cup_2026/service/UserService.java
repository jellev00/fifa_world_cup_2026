package com.example.fifa_world_cup_2026.service;

import com.example.fifa_world_cup_2026.dto.request.RegisterRequest;
import com.example.fifa_world_cup_2026.model.Role;
import com.example.fifa_world_cup_2026.model.User;
import com.example.fifa_world_cup_2026.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Gebruikersnaam is al in gebruik.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("E-mailadres is al in gebruik.");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Wachtwoorden komen niet overeen.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
