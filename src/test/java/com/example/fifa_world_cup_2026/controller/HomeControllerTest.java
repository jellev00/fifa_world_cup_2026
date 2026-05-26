package com.example.fifa_world_cup_2026.controller;

import com.example.fifa_world_cup_2026.config.SecurityConfig;
import com.example.fifa_world_cup_2026.repository.MatchRepository;
import com.example.fifa_world_cup_2026.service.CustomUserDetailsService;
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

@WebMvcTest(HomeController.class)
@Import(SecurityConfig.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MatchRepository matchRepository;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser
    void homepage_geeftStatus200() throws Exception {
        when(matchRepository.findAllByOrderByMatchDateTimeAsc())
                .thenReturn(List.of());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("matches"));
    }

    @Test
    @WithAnonymousUser
    void homepagina_toegankelijkZonderLogin() throws Exception {
        when(matchRepository.findAllByOrderByMatchDateTimeAsc())
                .thenReturn(List.of());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

}
