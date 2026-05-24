package com.example.fifa_world_cup_2026.exception;

public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException(Long id) {
        super("Wedstrijf met id " + id + " werd niet gevonden");
    }

    public MatchNotFoundException(String message) {
        super(message);
    }

}
