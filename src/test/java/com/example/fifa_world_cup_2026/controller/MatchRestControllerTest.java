package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.config.SecurityConfig;
import com.example.fifa_world_cup_2026.service.CustomUserDetailsService;
import com.example.fifa_world_cup_2026.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MatchRestController.class)
@Import(SecurityConfig.class)
public class MatchRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MatchService matchService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser
    void getAllMatches_geeftJsonTerug() throws Exception {
        when(matchService.getAllMatchesSorted()).thenReturn(List.of());

        mockMvc.perform(get("/api/matches"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    @Test
    @WithAnonymousUser
    void getAllMatches_toegankelijkZonderLogin() throws Exception {
        when(matchService.getAllMatchesSorted()).thenReturn(List.of());

        mockMvc.perform(get("/api/matches"))
                .andExpect(status().isOk());
    }

}
