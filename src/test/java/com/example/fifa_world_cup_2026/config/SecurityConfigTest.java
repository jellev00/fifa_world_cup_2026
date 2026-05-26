package com.example.fifa_world_cup_2026.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void adminPagina_zonderLogin_redirect() throws Exception {
        mockMvc.perform(get("/admin/matches"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void adminPagina_alsUser_geeftForbidden() throws Exception {
        mockMvc.perform(get("/admin/matches"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminPagina_alsAdmin_toegankelijk() throws Exception {
        mockMvc.perform(get("/admin/matches"))
                .andExpect(status().isOk());
    }

    @Test
    void loginPagina_altijdToegankelijk() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void top10Pagina_altijdToegankelijk() throws Exception {
        mockMvc.perform(get("/top10"))
                .andExpect(status().isOk());
    }

    @Test
    void teamPagina_zonderLogin_redirect() throws Exception {
        mockMvc.perform(get("/team/mijn-teams"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrlPattern("**/login"));
    }

}
