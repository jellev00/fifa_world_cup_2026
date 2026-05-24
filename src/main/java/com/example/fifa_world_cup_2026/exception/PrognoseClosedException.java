package com.example.fifa_world_cup_2026.exception;

public class PrognoseClosedException extends RuntimeException {

    public PrognoseClosedException() {
        super("Voorspelling zijn gesloten (minder dan 1 uur voor de aftrap).");
    }

    public PrognoseClosedException(String message) {
        super(message);
    }

}
