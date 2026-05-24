package com.example.fifa_world_cup_2026.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(String message) {
        super(message);
    }

    public TeamNotFoundException(Long id) {
        super("Team met id " + id + " werd niet gevonden.");
    }

}
