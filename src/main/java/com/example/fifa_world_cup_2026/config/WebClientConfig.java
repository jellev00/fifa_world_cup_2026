package com.example.fifa_world_cup_2026.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://restcountries.eu/rest/v2")
                .defaultHeader("Accept", "application/json")
                .build();
    }

}
