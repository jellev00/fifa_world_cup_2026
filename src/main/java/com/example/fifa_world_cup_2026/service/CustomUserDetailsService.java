package com.example.fifa_world_cup_2026.service;

import com.example.fifa_world_cup_2026.model.User;
import com.example.fifa_world_cup_2026.repository.UserRepository;
import lombok.RequiredArgsConstructor;
// TODO: nog eens kijken of deze dependencies niet veranderd kunnen worden naar iets wat we gezien hebben in de les
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Gebruiker niet gevonden: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

}
